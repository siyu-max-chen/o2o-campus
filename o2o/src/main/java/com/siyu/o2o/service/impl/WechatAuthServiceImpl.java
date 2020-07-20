package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.PersonInfoDao;
import com.siyu.o2o.dao.WechatAuthDao;
import com.siyu.o2o.dto.WechatAuthExecution;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.WechatAuth;
import com.siyu.o2o.enums.WechatAuthStateEnum;
import com.siyu.o2o.exceptions.WechatAuthOperationException;
import com.siyu.o2o.service.WechatAuthService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WechatAuthServiceImpl implements WechatAuthService {

   private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);
   @Autowired
   private WechatAuthDao wechatAuthDao;
   @Autowired
   private PersonInfoDao personInfoDao;


   public WechatAuth getWechatAuthByOpenId(String openId) {
      return this.wechatAuthDao.queryWechatInfoByOpenId(openId);
   }

   @Transactional
   public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
      if(wechatAuth != null && wechatAuth.getOpenId() != null) {
         try {
            wechatAuth.setCreateTime(new Date());
            if(wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() == null) {
               try {
                  wechatAuth.getPersonInfo().setCreateTime(new Date());
                  wechatAuth.getPersonInfo().setEnableStatus(Integer.valueOf(1));
                  PersonInfo e = wechatAuth.getPersonInfo();
                  int effectedNum = this.personInfoDao.insertPersonInfo(e);
                  wechatAuth.setPersonInfo(e);
                  if(effectedNum <= 0) {
                     throw new WechatAuthOperationException("添加用户信息失败");
                  }
               } catch (Exception var4) {
                  log.error("insertPersonInfo error:" + var4.toString());
                  throw new WechatAuthOperationException("insertPersonInfo error: " + var4.getMessage());
               }
            }

            int e1 = this.wechatAuthDao.insertWechatAuth(wechatAuth);
            if(e1 <= 0) {
               throw new WechatAuthOperationException("帐号创建失败");
            } else {
               return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, wechatAuth);
            }
         } catch (Exception var5) {
            log.error("insertWechatAuth error:" + var5.toString());
            throw new WechatAuthOperationException("insertWechatAuth error: " + var5.getMessage());
         }
      } else {
         return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
      }
   }

}
