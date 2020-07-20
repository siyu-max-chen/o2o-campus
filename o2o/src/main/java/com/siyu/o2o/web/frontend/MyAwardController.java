package com.siyu.o2o.web.frontend;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.siyu.o2o.dto.UserAwardMapExecution;
import com.siyu.o2o.entity.Award;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.UserAwardMap;
import com.siyu.o2o.enums.UserAwardMapStateEnum;
import com.siyu.o2o.service.AwardService;
import com.siyu.o2o.service.PersonInfoService;
import com.siyu.o2o.service.UserAwardMapService;
import com.siyu.o2o.util.CodeUtil;
import com.siyu.o2o.util.HttpServletRequestUtil;
import com.siyu.o2o.util.ShortNetAddressUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/frontend"})
public class MyAwardController {

   @Autowired
   private UserAwardMapService userAwardMapService;
   @Autowired
   private AwardService awardService;
   @Autowired
   private PersonInfoService personInfoService;
   private static String urlPrefix;
   private static String urlMiddle;
   private static String urlSuffix;
   private static String exchangeUrl;


   @RequestMapping(
      value = {"/getawardbyuserawardid"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getAwardbyId(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      long userAwardId = HttpServletRequestUtil.getLong(request, "userAwardId");
      if(userAwardId > -1L) {
         UserAwardMap userAwardMap = this.userAwardMapService.getUserAwardMapById(userAwardId);
         Award award = this.awardService.getAwardById(userAwardMap.getAward().getAwardId().longValue());
         modelMap.put("award", award);
         modelMap.put("usedStatus", userAwardMap.getUsedStatus());
         modelMap.put("userAwardMap", userAwardMap);
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty awardId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/listuserawardmapsbycustomer"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listUserAwardMapsByCustomer(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
      if(pageIndex > -1 && pageSize > -1 && user != null && user.getUserId() != null) {
         UserAwardMap userAwardMapCondition = new UserAwardMap();
         userAwardMapCondition.setUser(user);
         long shopId = HttpServletRequestUtil.getLong(request, "shopId");
         if(shopId > -1L) {
            Shop awardName = new Shop();
            awardName.setShopId(Long.valueOf(shopId));
            userAwardMapCondition.setShop(awardName);
         }

         String awardName1 = HttpServletRequestUtil.getString(request, "awardName");
         if(awardName1 != null) {
            Award ue = new Award();
            ue.setAwardName(awardName1);
            userAwardMapCondition.setAward(ue);
         }

         UserAwardMapExecution ue1 = this.userAwardMapService.listUserAwardMap(userAwardMapCondition, Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
         modelMap.put("userAwardMapList", ue1.getUserAwardMapList());
         modelMap.put("count", ue1.getCount());
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex or userId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/adduserawardmap"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map addUserAwardMap(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
      Long awardId = Long.valueOf(HttpServletRequestUtil.getLong(request, "awardId"));
      UserAwardMap userAwardMap = this.compactUserAwardMap4Add(user, awardId);
      if(userAwardMap != null) {
         try {
            UserAwardMapExecution e = this.userAwardMapService.addUserAwardMap(userAwardMap);
            if(e.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
               modelMap.put("success", Boolean.valueOf(true));
            } else {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", e.getStateInfo());
            }
         } catch (RuntimeException var7) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var7.toString());
            return modelMap;
         }
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "请选择领取的奖品");
      }

      return modelMap;
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

   @Value("${wechat.exchange.url}")
   public void setExchangeUrl(String exchangeUrl) {
      exchangeUrl = exchangeUrl;
   }

   @RequestMapping(
      value = {"/generateqrcode4award"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private void generateQRCode4Product(HttpServletRequest request, HttpServletResponse response) {
      long userAwardId = HttpServletRequestUtil.getLong(request, "userAwardId");
      UserAwardMap userAwardMap = this.userAwardMapService.getUserAwardMapById(userAwardId);
      PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
      if(userAwardMap != null && user != null && user.getUserId() != null && userAwardMap.getUser().getUserId() == user.getUserId()) {
         long timpStamp = System.currentTimeMillis();
         String content = "{aaauserAwardIdaaa:" + userAwardId + ",aaacustomerIdaaa:" + user.getUserId() + ",aaacreateTimeaaa:" + timpStamp + "}";

         try {
            String e = urlPrefix + exchangeUrl + urlMiddle + URLEncoder.encode(content, "UTF-8") + urlSuffix;
            String shortUrl = ShortNetAddressUtil.getShortURL(e);
            BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
            MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
         } catch (IOException var13) {
            var13.printStackTrace();
         }
      }

   }

   private UserAwardMap compactUserAwardMap4Add(PersonInfo user, Long awardId) {
      UserAwardMap userAwardMap = null;
      if(user != null && user.getUserId() != null && awardId.longValue() != -1L) {
         userAwardMap = new UserAwardMap();
         PersonInfo personInfo = this.personInfoService.getPersonInfoById(user.getUserId());
         Award award = this.awardService.getAwardById(awardId.longValue());
         userAwardMap.setUser(personInfo);
         userAwardMap.setAward(award);
         Shop shop = new Shop();
         shop.setShopId(award.getShopId());
         userAwardMap.setShop(shop);
         userAwardMap.setPoint(award.getPoint());
         userAwardMap.setCreateTime(new Date());
         userAwardMap.setUsedStatus(Integer.valueOf(1));
      }

      return userAwardMap;
   }
}
