package com.siyu.o2o.web.local;

import com.siyu.o2o.entity.LocalAuth;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.enums.LocalAuthStateEnum;
import com.siyu.o2o.exceptions.LocalAuthOperationException;
import com.siyu.o2o.service.LocalAuthService;
import com.siyu.o2o.util.CodeUtil;
import com.siyu.o2o.util.HttpServletRequestUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(
   value = {"local"},
   method = {RequestMethod.GET, RequestMethod.POST}
)
public class LocalAuthController {

   @Autowired
   private LocalAuthService localAuthService;


   @RequestMapping(
      value = {"/bindlocalauth"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map bindLocalAuth(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      if(!CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         String userName = HttpServletRequestUtil.getString(request, "userName");
         String password = HttpServletRequestUtil.getString(request, "password");
         PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
         if(userName != null && password != null && user != null && user.getUserId() != null) {
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUsername(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);
            LocalAuthExecution le = this.localAuthService.bindLocalAuth(localAuth);
            if(le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
               modelMap.put("success", Boolean.valueOf(true));
            } else {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", le.getStateInfo());
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "用户名和密码均不能为空");
         }

         return modelMap;
      }
   }

   @RequestMapping(
      value = {"/changelocalpwd"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map changeLocalPwd(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      if(!CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         String userName = HttpServletRequestUtil.getString(request, "userName");
         String email = HttpServletRequestUtil.getString(request, "email");
         String password = HttpServletRequestUtil.getString(request, "password");
         String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
         PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
         if(userName != null && password != null && newPassword != null && user != null && user.getUserId() != null && !password.equals(newPassword)) {
            try {
               LocalAuth e = this.localAuthService.getLocalAuthByUserId(user.getUserId().longValue());
               if(e == null || !e.getUsername().equals(userName)) {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", "输入的帐号非本次登录的帐号");
                  return modelMap;
               }

               LocalAuthExecution le = this.localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
               if(le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", le.getStateInfo());
               }
            } catch (LocalAuthOperationException var10) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var10.toString());
               return modelMap;
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "请输入密码");
         }

         return modelMap;
      }
   }

   @RequestMapping(
      value = {"/logincheck"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map logincheck(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
      if(needVerify && !CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         String userName = HttpServletRequestUtil.getString(request, "userName");
         String password = HttpServletRequestUtil.getString(request, "password");
         if(userName != null && password != null) {
            LocalAuth localAuth = this.localAuthService.getLocalAuthByUsernameAndPwd(userName, password);
            if(localAuth != null) {
               modelMap.put("success", Boolean.valueOf(true));
               request.getSession().setAttribute("user", localAuth.getPersonInfo());
            } else {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", "用户名或密码错误");
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "用户名和密码均不能为空");
         }

         return modelMap;
      }
   }

   @RequestMapping(
      value = {"/logincheckemail"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map logincheckemail(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
      if(needVerify && !CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         String email = HttpServletRequestUtil.getString(request, "userName");
         String password = HttpServletRequestUtil.getString(request, "password");
         if(email != null && password != null) {
            LocalAuth localAuth = this.localAuthService.getLocalAuthByEmailAndPwd(email, password);
            if(localAuth != null) {
               modelMap.put("success", Boolean.valueOf(true));
               request.getSession().setAttribute("user", localAuth.getPersonInfo());
            } else {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", "用户名或密码错误");
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "用户名和密码均不能为空");
         }

         return modelMap;
      }
   }

   @RequestMapping(
      value = {"/createnewuser"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map createnewuser(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
      if(needVerify && !CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "Wrong Verification Code!");
         return modelMap;
      } else {
         String userName = HttpServletRequestUtil.getString(request, "userName");
         String email = HttpServletRequestUtil.getString(request, "email");
         String password = HttpServletRequestUtil.getString(request, "password");
         if(email != null && password != null && userName != null) {
            try {
               LocalAuthExecution e = this.localAuthService.registerLocalAuth(userName, email, password);
               if(e.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
                  request.getSession().setAttribute("user", e.getLocalAuth().getPersonInfo());
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", e.getStateInfo());
               }
            } catch (LocalAuthOperationException var8) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var8.toString());
               return modelMap;
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "Username, Email, Password cannot be empty!");
         }

         return modelMap;
      }
   }

   @RequestMapping(
      value = {"/checkuserstatus"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map checkuserstatus(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      modelMap.put("success", Boolean.valueOf(true));
      modelMap.put("user", request.getSession().getAttribute("user"));
      return modelMap;
   }

   @RequestMapping(
      value = {"/checklocalauth"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map checklocalauth(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      Object requestResult = request.getSession().getAttribute("user");
      PersonInfo personInfo = null;
      if(requestResult != null && requestResult instanceof PersonInfo) {
         personInfo = (PersonInfo)requestResult;
      }

      if(personInfo != null) {
         try {
            LocalAuth e = this.localAuthService.getLocalAuthByUserId(personInfo.getUserId().longValue());
            modelMap.put("success", Boolean.valueOf(true));
            modelMap.put("localAuth", e);
         } catch (LocalAuthOperationException var6) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var6.toString());
            return modelMap;
         }
      } else {
         modelMap.put("success", Boolean.valueOf(false));
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/logout"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map logout(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      request.getSession().setAttribute("user", (Object)null);
      modelMap.put("success", Boolean.valueOf(true));
      return modelMap;
   }
}
