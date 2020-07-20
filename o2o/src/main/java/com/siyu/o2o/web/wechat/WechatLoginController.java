package com.siyu.o2o.web.wechat;

import com.siyu.o2o.dto.UserAccessToken;
import com.siyu.o2o.dto.WechatAuthExecution;
import com.siyu.o2o.dto.WechatUser;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.WechatAuth;
import com.siyu.o2o.enums.WechatAuthStateEnum;
import com.siyu.o2o.service.PersonInfoService;
import com.siyu.o2o.service.WechatAuthService;
import com.siyu.o2o.util.wechat.WechatUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"wechatlogin"})
public class WechatLoginController {

   private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);
   private static final String FRONTEND = "1";
   private static final String SHOPEND = "2";
   @Autowired
   private PersonInfoService personInfoService;
   @Autowired
   private WechatAuthService wechatAuthService;


   @RequestMapping(
      value = {"/logincheck"},
      method = {RequestMethod.GET}
   )
   public String doGet(HttpServletRequest request, HttpServletResponse response) {
      log.debug("weixin login get...");
      String code = request.getParameter("code");
      String roleType = request.getParameter("state");
      log.debug("weixin login code:" + code);
      WechatUser user = null;
      String openId = null;
      WechatAuth auth = null;
      if(null != code) {
         try {
            UserAccessToken personInfo = WechatUtil.getUserAccessToken(code);
            log.debug("weixin login token:" + personInfo.toString());
            String we = personInfo.getAccessToken();
            openId = personInfo.getOpenId();
            user = WechatUtil.getUserInfo(we, openId);
            log.debug("weixin login user:" + user.toString());
            request.getSession().setAttribute("openId", openId);
            auth = this.wechatAuthService.getWechatAuthByOpenId(openId);
         } catch (IOException var10) {
            log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + var10.toString());
            var10.printStackTrace();
         }
      }

      if(auth == null) {
         PersonInfo personInfo1 = WechatUtil.getPersonInfoFromRequest(user);
         auth = new WechatAuth();
         auth.setOpenId(openId);
         if("1".equals(roleType)) {
            personInfo1.setUserType(Integer.valueOf(1));
         } else {
            personInfo1.setUserType(Integer.valueOf(2));
         }

         auth.setPersonInfo(personInfo1);
         WechatAuthExecution we1 = this.wechatAuthService.register(auth);
         if(we1.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
            return null;
         }

         personInfo1 = this.personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
         request.getSession().setAttribute("user", personInfo1);
      } else {
         request.getSession().setAttribute("user", auth.getPersonInfo());
      }

      return "1".equals(roleType)?"frontend/index":"shop/shoplist";
   }

}
