package com.siyu.o2o.web.shopadmin;

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
@RequestMapping({"/shopadmin"})
public class UserShopManagementController {

   @Autowired
   private UserShopMapService userShopMapService;


   @RequestMapping(
      value = {"/listusershopmapsbyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listUserShopMapsByShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      if(pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
         UserShopMap userShopMapCondition = new UserShopMap();
         userShopMapCondition.setShop(currentShop);
         String userName = HttpServletRequestUtil.getString(request, "userName");
         if(userName != null) {
            PersonInfo ue = new PersonInfo();
            ue.setName(userName);
            userShopMapCondition.setUser(ue);
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
