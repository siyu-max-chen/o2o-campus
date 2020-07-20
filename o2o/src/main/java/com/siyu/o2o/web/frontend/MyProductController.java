package com.siyu.o2o.web.frontend;

import com.siyu.o2o.dto.UserProductMapExecution;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.UserProductMap;
import com.siyu.o2o.service.UserProductMapService;
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
@RequestMapping({"/frontend"})
public class MyProductController {

   @Autowired
   private UserProductMapService userProductMapService;


   @RequestMapping(
      value = {"/listuserproductmapsbycustomer"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listUserProductMapsByCustomer(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
      if(pageIndex > -1 && pageSize > -1 && user != null && user.getUserId().longValue() != -1L) {
         UserProductMap userProductMapCondition = new UserProductMap();
         userProductMapCondition.setUser(user);
         long shopId = HttpServletRequestUtil.getLong(request, "shopId");
         if(shopId > -1L) {
            Shop productName = new Shop();
            productName.setShopId(Long.valueOf(shopId));
            userProductMapCondition.setShop(productName);
         }

         String productName1 = HttpServletRequestUtil.getString(request, "productName");
         if(productName1 != null) {
            Product ue = new Product();
            ue.setProductName(productName1);
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
}
