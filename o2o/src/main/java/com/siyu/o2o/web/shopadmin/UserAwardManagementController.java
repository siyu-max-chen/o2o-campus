package com.siyu.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.dto.ShopAuthMapExecution;
import com.siyu.o2o.dto.UserAccessToken;
import com.siyu.o2o.dto.UserAwardMapExecution;
import com.siyu.o2o.dto.WechatInfo;
import com.siyu.o2o.entity.Award;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.ShopAuthMap;
import com.siyu.o2o.entity.UserAwardMap;
import com.siyu.o2o.entity.WechatAuth;
import com.siyu.o2o.enums.UserAwardMapStateEnum;
import com.siyu.o2o.service.PersonInfoService;
import com.siyu.o2o.service.ShopAuthMapService;
import com.siyu.o2o.service.UserAwardMapService;
import com.siyu.o2o.service.WechatAuthService;
import com.siyu.o2o.util.HttpServletRequestUtil;
import com.siyu.o2o.util.wechat.WechatUtil;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/shopadmin"})
public class UserAwardManagementController {

   @Autowired
   private UserAwardMapService userAwardMapService;
   @Autowired
   private PersonInfoService personInfoService;
   @Autowired
   private ShopAuthMapService shopAuthMapService;
   @Autowired
   private WechatAuthService wechatAuthService;


   @RequestMapping(
      value = {"/listuserawardmapsbyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listUserAwardMapsByShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      if(pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
         UserAwardMap userAwardMap = new UserAwardMap();
         userAwardMap.setShop(currentShop);
         String awardName = HttpServletRequestUtil.getString(request, "awardName");
         if(awardName != null) {
            Award ue = new Award();
            ue.setAwardName(awardName);
            userAwardMap.setAward(ue);
         }

         UserAwardMapExecution ue1 = this.userAwardMapService.listReceivedUserAwardMap(userAwardMap, Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
         modelMap.put("userAwardMapList", ue1.getUserAwardMapList());
         modelMap.put("count", ue1.getCount());
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/exchangeaward"},
      method = {RequestMethod.GET}
   )
   private String exchangeAward(HttpServletRequest request, HttpServletResponse response) throws IOException {
      WechatAuth auth = this.getOperatorInfo(request);
      if(auth != null) {
         PersonInfo operator = this.personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
         request.getSession().setAttribute("user", operator);
         String qrCodeinfo = new String(URLDecoder.decode(HttpServletRequestUtil.getString(request, "state"), "UTF-8"));
         ObjectMapper mapper = new ObjectMapper();
         WechatInfo wechatInfo = null;

         try {
            wechatInfo = (WechatInfo)mapper.readValue(qrCodeinfo.replace("aaa", "\""), WechatInfo.class);
         } catch (Exception var13) {
            return "shop/operationfail";
         }

         if(!this.checkQRCodeInfo(wechatInfo)) {
            return "shop/operationfail";
         }

         Long userAwardId = wechatInfo.getUserAwardId();
         Long customerId = wechatInfo.getCustomerId();
         UserAwardMap userAwardMap = this.compactUserAwardMap4Exchange(customerId, userAwardId, operator);
         if(userAwardMap != null) {
            try {
               if(!this.checkShopAuth(operator.getUserId().longValue(), userAwardMap)) {
                  return "shop/operationfail";
               }

               UserAwardMapExecution e = this.userAwardMapService.modifyUserAwardMap(userAwardMap);
               if(e.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
                  return "shop/operationsuccess";
               }
            } catch (RuntimeException var12) {
               return "shop/operationfail";
            }
         }
      }

      return "shop/operationfail";
   }

   private WechatAuth getOperatorInfo(HttpServletRequest request) {
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

   private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
      if(wechatInfo != null && wechatInfo.getUserAwardId() != null && wechatInfo.getCustomerId() != null && wechatInfo.getCreateTime() != null) {
         long nowTime = System.currentTimeMillis();
         return nowTime - wechatInfo.getCreateTime().longValue() <= 600000L;
      } else {
         return false;
      }
   }

   private UserAwardMap compactUserAwardMap4Exchange(Long customerId, Long userAwardId, PersonInfo operator) {
      UserAwardMap userAwardMap = null;
      if(customerId != null && userAwardId != null && operator != null) {
         userAwardMap = this.userAwardMapService.getUserAwardMapById(userAwardId.longValue());
         userAwardMap.setUsedStatus(Integer.valueOf(1));
         PersonInfo customer = new PersonInfo();
         customer.setUserId(customerId);
         userAwardMap.setUser(customer);
         userAwardMap.setOperator(operator);
      }

      return userAwardMap;
   }

   private boolean checkShopAuth(long userId, UserAwardMap userAwardMap) {
      ShopAuthMapExecution shopAuthMapExecution = this.shopAuthMapService.listShopAuthMapByShopId(userAwardMap.getShop().getShopId(), Integer.valueOf(1), Integer.valueOf(1000));
      Iterator var5 = shopAuthMapExecution.getShopAuthMapList().iterator();

      ShopAuthMap shopAuthMap;
      do {
         if(!var5.hasNext()) {
            return false;
         }

         shopAuthMap = (ShopAuthMap)var5.next();
      } while(shopAuthMap.getEmployee().getUserId().longValue() != userId || shopAuthMap.getEnableStatus().intValue() != 1);

      return true;
   }
}
