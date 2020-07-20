package com.siyu.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.siyu.o2o.dto.ShopAuthMapExecution;
import com.siyu.o2o.dto.UserAccessToken;
import com.siyu.o2o.dto.WechatInfo;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.ShopAuthMap;
import com.siyu.o2o.entity.WechatAuth;
import com.siyu.o2o.enums.ShopAuthMapStateEnum;
import com.siyu.o2o.service.PersonInfoService;
import com.siyu.o2o.service.ShopAuthMapService;
import com.siyu.o2o.service.WechatAuthService;
import com.siyu.o2o.util.CodeUtil;
import com.siyu.o2o.util.HttpServletRequestUtil;
import com.siyu.o2o.util.ShortNetAddressUtil;
import com.siyu.o2o.util.wechat.WechatUtil;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/shopadmin"})
public class ShopAuthManagementController {

   @Autowired
   private ShopAuthMapService shopAuthMapService;
   private static String urlPrefix;
   private static String urlMiddle;
   private static String urlSuffix;
   private static String authUrl;
   @Autowired
   private WechatAuthService wechatAuthService;
   @Autowired
   private PersonInfoService personInfoService;


   @RequestMapping(
      value = {"/listshopauthmapsbyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listShopAuthMapsByShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      if(pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
         ShopAuthMapExecution se = this.shopAuthMapService.listShopAuthMapByShopId(currentShop.getShopId(), Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
         modelMap.put("shopAuthMapList", se.getShopAuthMapList());
         modelMap.put("count", se.getCount());
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/getshopauthmapbyid"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getShopAuthMapById(@RequestParam Long shopAuthId) {
      HashMap modelMap = new HashMap();
      if(shopAuthId != null && shopAuthId.longValue() > -1L) {
         ShopAuthMap shopAuthMap = this.shopAuthMapService.getShopAuthMapById(shopAuthId);
         modelMap.put("shopAuthMap", shopAuthMap);
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty shopAuthId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/modifyshopauthmap"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map modifyShopAuthMap(String shopAuthMapStr, HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
      if(!statusChange && !CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         ObjectMapper mapper = new ObjectMapper();
         ShopAuthMap shopAuthMap = null;

         try {
            shopAuthMap = (ShopAuthMap)mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
         } catch (Exception var9) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var9.toString());
            return modelMap;
         }

         if(shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
            try {
               if(!this.checkPermission(shopAuthMap.getShopAuthId())) {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", "无法对店家本身权限做操作(已是店铺的最高权限)");
                  return modelMap;
               }

               ShopAuthMapExecution e = this.shopAuthMapService.modifyShopAuthMap(shopAuthMap);
               if(e.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", e.getStateInfo());
               }
            } catch (RuntimeException var8) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var8.toString());
               return modelMap;
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "请输入要修改的授权信息");
         }

         return modelMap;
      }
   }

   private boolean checkPermission(Long shopAuthId) {
      ShopAuthMap grantedPerson = this.shopAuthMapService.getShopAuthMapById(shopAuthId);
      return grantedPerson.getTitleFlag().intValue() != 0;
   }

   @Value("${wechat.prefix}")
   public void setUrlPrefix(String urlPrefix) {
      urlPrefix = urlPrefix;
   }

   @Value("${wechat.middle}")
   public void setUrlMiddle(String urlMiddle) {
      urlMiddle = urlMiddle;
   }

   @Value("${wechat.suffix}")
   public void setUrlSuffix(String urlSuffix) {
      urlSuffix = urlSuffix;
   }

   @Value("${wechat.auth.url}")
   public void setAuthUrl(String authUrl) {
      authUrl = authUrl;
   }

   @RequestMapping(
      value = {"/generateqrcode4shopauth"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private void generateQRCode4ShopAuth(HttpServletRequest request, HttpServletResponse response) {
      Shop shop = (Shop)request.getSession().getAttribute("currentShop");
      if(shop != null && shop.getShopId() != null) {
         long timpStamp = System.currentTimeMillis();
         String content = "{aaashopIdaaa:" + shop.getShopId() + ",aaacreateTimeaaa:" + timpStamp + "}";

         try {
            String e = urlPrefix + authUrl + urlMiddle + URLEncoder.encode(content, "UTF-8") + urlSuffix;
            String shortUrl = ShortNetAddressUtil.getShortURL(e);
            BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
            MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
         } catch (IOException var10) {
            var10.printStackTrace();
         }
      }

   }

   @RequestMapping(
      value = {"/addshopauthmap"},
      method = {RequestMethod.GET}
   )
   private String addShopAuthMap(HttpServletRequest request, HttpServletResponse response) throws IOException {
      WechatAuth auth = this.getEmployeeInfo(request);
      if(auth != null) {
         PersonInfo user = this.personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
         request.getSession().setAttribute("user", user);
         String qrCodeinfo = new String(URLDecoder.decode(HttpServletRequestUtil.getString(request, "state"), "UTF-8"));
         ObjectMapper mapper = new ObjectMapper();
         WechatInfo wechatInfo = null;

         try {
            wechatInfo = (WechatInfo)mapper.readValue(qrCodeinfo.replace("aaa", "\""), WechatInfo.class);
         } catch (Exception var14) {
            return "shop/operationfail";
         }

         if(!this.checkQRCodeInfo(wechatInfo)) {
            return "shop/operationfail";
         } else {
            ShopAuthMapExecution allMapList = this.shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(), Integer.valueOf(1), Integer.valueOf(999));
            List shopAuthList = allMapList.getShopAuthMapList();
            Iterator e = shopAuthList.iterator();

            ShopAuthMap shop;
            do {
               if(!e.hasNext()) {
                  try {
                     ShopAuthMap e1 = new ShopAuthMap();
                     Shop shop1 = new Shop();
                     shop1.setShopId(wechatInfo.getShopId());
                     e1.setShop(shop1);
                     e1.setEmployee(user);
                     e1.setTitle("员工");
                     e1.setTitleFlag(Integer.valueOf(1));
                     ShopAuthMapExecution se = this.shopAuthMapService.addShopAuthMap(e1);
                     if(se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
                        return "shop/operationsuccess";
                     }

                     return "shop/operationfail";
                  } catch (RuntimeException var13) {
                     return "shop/operationfail";
                  }
               }

               shop = (ShopAuthMap)e.next();
            } while(shop.getEmployee().getUserId() != user.getUserId());

            return "shop/operationfail";
         }
      } else {
         return "shop/operationfail";
      }
   }

   private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
      if(wechatInfo != null && wechatInfo.getShopId() != null && wechatInfo.getCreateTime() != null) {
         long nowTime = System.currentTimeMillis();
         return nowTime - wechatInfo.getCreateTime().longValue() <= 600000L;
      } else {
         return false;
      }
   }

   private WechatAuth getEmployeeInfo(HttpServletRequest request) {
      String code = request.getParameter("code");
      WechatAuth auth = null;
      if(null != code) {
         try {
            UserAccessToken token = WechatUtil.getUserAccessToken(code);
            String e = token.getOpenId();
            request.getSession().setAttribute("openId", e);
            auth = this.wechatAuthService.getWechatAuthByOpenId(e);
         } catch (IOException var6) {
            var6.printStackTrace();
         }
      }

      return auth;
   }
}
