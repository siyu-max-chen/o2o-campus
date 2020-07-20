package com.siyu.o2o.web.frontend;

import com.siyu.o2o.dto.UserShopMapExecution;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.UserShopMap;
import com.siyu.o2o.service.UserShopMapService;
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
public class MyShopPointController {

   @Autowired
   private UserShopMapService userShopMapService;


   @RequestMapping(
      value = {"/listusershopmapsbycustomer"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listUserShopMapsByCustomer(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
      if(pageIndex > -1 && pageSize > -1 && user != null && user.getUserId() != null) {
         UserShopMap userShopMapCondition = new UserShopMap();
         userShopMapCondition.setUser(user);
         long shopId = HttpServletRequestUtil.getLong(request, "shopId");
         if(shopId > -1L) {
            Shop shopName = new Shop();
            shopName.setShopId(Long.valueOf(shopId));
            userShopMapCondition.setShop(shopName);
         }

         String shopName1 = HttpServletRequestUtil.getString(request, "shopName");
         if(shopName1 != null) {
            Shop ue = new Shop();
            ue.setShopName(shopName1);
            userShopMapCondition.setShop(ue);
         }

         UserShopMapExecution ue1 = this.userShopMapService.listUserShopMap(userShopMapCondition, pageIndex, pageSize);
         modelMap.put("userShopMapList", ue1.getUserShopMapList());
         modelMap.put("count", ue1.getCount());
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
      }

      return modelMap;
   }
}
