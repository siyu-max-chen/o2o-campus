package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.LocalAuthDao;
import com.siyu.o2o.dao.PersonInfoDao;
import com.siyu.o2o.entity.LocalAuth;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.enums.LocalAuthStateEnum;
import com.siyu.o2o.exceptions.LocalAuthOperationException;
import com.siyu.o2o.service.LocalAuthService;
import com.siyu.o2o.util.MD5;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

   @Autowired
   private LocalAuthDao localAuthDao;
   @Autowired
   private PersonInfoDao personInfoDao;


   public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
      System.out.println(username + " " + password + " " + MD5.getMd5(password));
      return this.localAuthDao.queryLocalByUserNameAndPwd(username, MD5.getMd5(password));
   }

   public LocalAuth getLocalAuthByEmailAndPwd(String email, String password) {
      System.out.println(email + " " + password + " " + MD5.getMd5(password));
      return this.localAuthDao.queryLocalByEmailAndPwd(email, MD5.getMd5(password));
   }

   public LocalAuth getLocalAuthByUserId(long userId) {
      return this.localAuthDao.queryLocalByUserId(userId);
   }

   @Transactional
   public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
      if(localAuth != null && localAuth.getPassword() != null && localAuth.getUsername() != null && localAuth.getPersonInfo() != null && localAuth.getPersonInfo().getUserId() != null) {
         LocalAuth tempAuth = this.localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId().longValue());
         if(tempAuth != null) {
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
         } else {
            try {
               localAuth.setCreateTime(new Date());
               localAuth.setLastEditTime(new Date());
               localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
               int e = this.localAuthDao.insertLocalAuth(localAuth);
               if(e <= 0) {
                  throw new LocalAuthOperationException("帐号绑定失败");
               } else {
                  return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
               }
            } catch (Exception var4) {
               throw new LocalAuthOperationException("insertLocalAuth error: " + var4.getMessage());
            }
         }
      } else {
         return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
      }
   }

   @Transactional
   public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) throws LocalAuthOperationException {
      if(userId != null && userName != null && password != null && newPassword != null && !password.equals(newPassword)) {
         try {
            int e = this.localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
            if(e <= 0) {
               throw new LocalAuthOperationException("更新密码失败");
            } else {
               return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            }
         } catch (Exception var6) {
            throw new LocalAuthOperationException("更新密码失败:" + var6.toString());
         }
      } else {
         return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
      }
   }

   public LocalAuthExecution registerLocalAuth(String username, String email, String password) throws LocalAuthOperationException {
      LocalAuth localAuth = this.localAuthDao.queryLocalByUserName(username);
      if(localAuth != null) {
         return new LocalAuthExecution(LocalAuthStateEnum.USER_NAME_USED);
      } else {
         localAuth = this.localAuthDao.queryLocalByEmail(email);
         if(localAuth != null) {
            return new LocalAuthExecution(LocalAuthStateEnum.EMAIL_USED);
         } else {
            try {
               PersonInfo e = new PersonInfo();
               e.setEnableStatus(Integer.valueOf(1));
               e.setUserType(Integer.valueOf(1));
               e.setCreateTime(new Date());
               e.setLastEditTime(new Date());
               int effectedNum = this.personInfoDao.insertPersonInfo(e);
               if(effectedNum <= 0) {
                  throw new LocalAuthOperationException("Failed to create a new account!");
               } else {
                  Long userId = e.getUserId();
                  System.out.println("new created user id is: " + userId);
                  localAuth = new LocalAuth();
                  localAuth.setUsername(username);
                  localAuth.setEmail(email);
                  localAuth.setPassword(password);
                  localAuth.setPersonInfo(e);
                  localAuth.setCreateTime(new Date());
                  localAuth.setLastEditTime(new Date());
                  localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
                  effectedNum = this.localAuthDao.insertLocalAuth(localAuth);
                  if(effectedNum <= 0) {
                     throw new LocalAuthOperationException("Failed to create a new account!");
                  } else {
                     return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
                  }
               }
            } catch (Exception var8) {
               throw new LocalAuthOperationException("insertLocalAuth error: " + var8.getMessage());
            }
         }
      }
   }
}
