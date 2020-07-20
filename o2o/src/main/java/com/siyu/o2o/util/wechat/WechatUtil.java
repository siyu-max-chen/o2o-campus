package com.siyu.o2o.util.wechat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.dto.UserAccessToken;
import com.siyu.o2o.dto.WechatUser;
import com.siyu.o2o.entity.PersonInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WechatUtil {

   private static Logger log = LoggerFactory.getLogger(WechatUtil.class);


   public static UserAccessToken getUserAccessToken(String code) throws IOException {
      String appId = "wx9325b2855284c4b4";
      log.debug("appId:" + appId);
      String appsecret = "362865f627567414aa854b97365bf0be";
      log.debug("secret:" + appsecret);
      String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
      String tokenStr = httpsRequest(url, "GET", (String)null);
      log.debug("userAccessToken:" + tokenStr);
      UserAccessToken token = new UserAccessToken();
      ObjectMapper objectMapper = new ObjectMapper();

      try {
         token = (UserAccessToken)objectMapper.readValue(tokenStr, UserAccessToken.class);
      } catch (JsonParseException var8) {
         log.error("获取用户accessToken失败: " + var8.getMessage());
         var8.printStackTrace();
      } catch (JsonMappingException var9) {
         log.error("获取用户accessToken失败: " + var9.getMessage());
         var9.printStackTrace();
      } catch (IOException var10) {
         log.error("获取用户accessToken失败: " + var10.getMessage());
         var10.printStackTrace();
      }

      if(token == null) {
         log.error("获取用户accessToken失败。");
         return null;
      } else {
         return token;
      }
   }

   public static WechatUser getUserInfo(String accessToken, String openId) {
      String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
      String userStr = httpsRequest(url, "GET", (String)null);
      log.debug("user info :" + userStr);
      WechatUser user = new WechatUser();
      ObjectMapper objectMapper = new ObjectMapper();

      try {
         user = (WechatUser)objectMapper.readValue(userStr, WechatUser.class);
      } catch (JsonParseException var7) {
         log.error("获取用户信息失败: " + var7.getMessage());
         var7.printStackTrace();
      } catch (JsonMappingException var8) {
         log.error("获取用户信息失败: " + var8.getMessage());
         var8.printStackTrace();
      } catch (IOException var9) {
         log.error("获取用户信息失败: " + var9.getMessage());
         var9.printStackTrace();
      }

      if(user == null) {
         log.error("获取用户信息失败。");
         return null;
      } else {
         return user;
      }
   }

   public static PersonInfo getPersonInfoFromRequest(WechatUser user) {
      PersonInfo personInfo = new PersonInfo();
      personInfo.setName(user.getNickName());
      personInfo.setGender(user.getSex() + "");
      personInfo.setProfileImg(user.getHeadimgurl());
      personInfo.setEnableStatus(Integer.valueOf(1));
      return personInfo;
   }

   public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
      StringBuffer buffer = new StringBuffer();

      try {
         TrustManager[] e = new TrustManager[]{new MyX509TrustManager()};
         SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
         sslContext.init((KeyManager[])null, e, new SecureRandom());
         SSLSocketFactory ssf = sslContext.getSocketFactory();
         URL url = new URL(requestUrl);
         HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
         httpUrlConn.setSSLSocketFactory(ssf);
         httpUrlConn.setDoOutput(true);
         httpUrlConn.setDoInput(true);
         httpUrlConn.setUseCaches(false);
         httpUrlConn.setRequestMethod(requestMethod);
         if("GET".equalsIgnoreCase(requestMethod)) {
            httpUrlConn.connect();
         }

         OutputStream inputStream;
         if(null != outputStr) {
            inputStream = httpUrlConn.getOutputStream();
            inputStream.write(outputStr.getBytes("UTF-8"));
            inputStream.close();
         }

         InputStream inputStream1 = httpUrlConn.getInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream1, "utf-8");
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         String str = null;

         while((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
         }

         bufferedReader.close();
         inputStreamReader.close();
         inputStream1.close();
         inputStream = null;
         httpUrlConn.disconnect();
         log.debug("https buffer:" + buffer.toString());
      } catch (ConnectException var13) {
         log.error("Weixin server connection timed out.");
      } catch (Exception var14) {
         log.error("https request error:{}", var14);
      }

      return buffer.toString();
   }

}
