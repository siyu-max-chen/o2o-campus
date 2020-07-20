package com.siyu.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.dto.EchartSeries;
import com.siyu.o2o.dto.EchartXAxis;
import com.siyu.o2o.dto.ShopAuthMapExecution;
import com.siyu.o2o.dto.UserAccessToken;
import com.siyu.o2o.dto.UserProductMapExecution;
import com.siyu.o2o.dto.WechatInfo;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.entity.ProductSellDaily;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.ShopAuthMap;
import com.siyu.o2o.entity.UserProductMap;
import com.siyu.o2o.entity.WechatAuth;
import com.siyu.o2o.enums.UserProductMapStateEnum;
import com.siyu.o2o.service.ProductSellDailyService;
import com.siyu.o2o.service.ProductService;
import com.siyu.o2o.service.ShopAuthMapService;
import com.siyu.o2o.service.UserProductMapService;
import com.siyu.o2o.service.WechatAuthService;
import com.siyu.o2o.util.HttpServletRequestUtil;
import com.siyu.o2o.util.wechat.WechatUtil;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
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
public class UserProductManagementController {

   @Autowired
   private UserProductMapService userProductMapService;
   @Autowired
   private ProductSellDailyService productSellDailyService;
   @Autowired
   private WechatAuthService wechatAuthService;
   @Autowired
   private ShopAuthMapService shopAuthMapService;
   @Autowired
   private ProductService productService;


   @RequestMapping(
      value = {"/listuserproductmapsbyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listUserProductMapsByShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      if(pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
         UserProductMap userProductMapCondition = new UserProductMap();
         userProductMapCondition.setShop(currentShop);
         String productName = HttpServletRequestUtil.getString(request, "productName");
         if(productName != null) {
            Product ue = new Product();
            ue.setProductName(productName);
            userProductMapCondition.setProduct(ue);
         }

         UserProductMapExecution ue1 = this.userProductMapService.listUserProductMap(userProductMapCondition, Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
         modelMap.put("userProductMapList", ue1.getUserProductMapList());
         modelMap.put("count", ue1.getCount());
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/listproductselldailyinfobyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listProductSellDailyInfobyShop(HttpServletRequest request) throws ParseException {
      HashMap modelMap = new HashMap();
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      if(currentShop != null && currentShop.getShopId() != null) {
         ProductSellDaily productSellDailyCondition = new ProductSellDaily();
         productSellDailyCondition.setShop(currentShop);
         Calendar calendar = Calendar.getInstance();
         calendar.add(5, -1);
         Date endTime = calendar.getTime();
         calendar.add(5, -6);
         Date beginTime = calendar.getTime();
         List productSellDailyList = this.productSellDailyService.listProductSellDaily(productSellDailyCondition, beginTime, endTime);
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         LinkedHashSet legendData = new LinkedHashSet();
         LinkedHashSet xData = new LinkedHashSet();
         ArrayList series = new ArrayList();
         ArrayList totalList = new ArrayList();
         String currentProductName = "";

         for(int xAxis = 0; xAxis < productSellDailyList.size(); ++xAxis) {
            ProductSellDaily exa = (ProductSellDaily)productSellDailyList.get(xAxis);
            legendData.add(exa.getProduct().getProductName());
            xData.add(sdf.format(exa.getCreateTime()));
            EchartSeries es;
            if(!currentProductName.equals(exa.getProduct().getProductName()) && !currentProductName.isEmpty()) {
               es = new EchartSeries();
               es.setName(currentProductName);
               es.setData(totalList.subList(0, totalList.size()));
               series.add(es);
               totalList = new ArrayList();
               currentProductName = exa.getProduct().getProductName();
               totalList.add(exa.getTotal());
            } else {
               totalList.add(exa.getTotal());
               currentProductName = exa.getProduct().getProductName();
            }

            if(xAxis == productSellDailyList.size() - 1) {
               es = new EchartSeries();
               es.setName(currentProductName);
               es.setData(totalList.subList(0, totalList.size()));
               series.add(es);
            }
         }

         modelMap.put("series", series);
         modelMap.put("legendData", legendData);
         ArrayList var18 = new ArrayList();
         EchartXAxis var19 = new EchartXAxis();
         var19.setData(xData);
         var18.add(var19);
         modelMap.put("xAxis", var18);
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty shopId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/adduserproductmap"},
      method = {RequestMethod.GET}
   )
   private String addUserProductMap(HttpServletRequest request, HttpServletResponse response) throws IOException {
      WechatAuth auth = this.getOperatorInfo(request);
      if(auth != null) {
         PersonInfo operator = auth.getPersonInfo();
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

         Long productId = wechatInfo.getProductId();
         Long customerId = wechatInfo.getCustomerId();
         UserProductMap userProductMap = this.compactUserProductMap4Add(customerId, productId, auth.getPersonInfo());
         if(userProductMap != null && customerId.longValue() != -1L) {
            try {
               if(!this.checkShopAuth(operator.getUserId().longValue(), userProductMap)) {
                  return "shop/operationfail";
               }

               UserProductMapExecution e = this.userProductMapService.addUserProductMap(userProductMap);
               if(e.getState() == UserProductMapStateEnum.SUCCESS.getState()) {
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
      if(wechatInfo != null && wechatInfo.getProductId() != null && wechatInfo.getCustomerId() != null && wechatInfo.getCreateTime() != null) {
         long nowTime = System.currentTimeMillis();
         return nowTime - wechatInfo.getCreateTime().longValue() <= 600000L;
      } else {
         return false;
      }
   }

   private UserProductMap compactUserProductMap4Add(Long customerId, Long productId, PersonInfo operator) {
      UserProductMap userProductMap = null;
      if(customerId != null && productId != null) {
         userProductMap = new UserProductMap();
         PersonInfo customer = new PersonInfo();
         customer.setUserId(customerId);
         Product product = this.productService.getProductById(productId.longValue());
         userProductMap.setProduct(product);
         userProductMap.setShop(product.getShop());
         userProductMap.setUser(customer);
         userProductMap.setPoint(product.getPoint());
         userProductMap.setCreateTime(new Date());
         userProductMap.setOperator(operator);
      }

      return userProductMap;
   }

   private boolean checkShopAuth(long userId, UserProductMap userProductMap) {
      ShopAuthMapExecution shopAuthMapExecution = this.shopAuthMapService.listShopAuthMapByShopId(userProductMap.getShop().getShopId(), Integer.valueOf(1), Integer.valueOf(1000));
      Iterator var5 = shopAuthMapExecution.getShopAuthMapList().iterator();

      ShopAuthMap shopAuthMap;
      do {
         if(!var5.hasNext()) {
            return false;
         }

         shopAuthMap = (ShopAuthMap)var5.next();
      } while(shopAuthMap.getEmployee().getUserId().longValue() != userId);

      return true;
   }
}
