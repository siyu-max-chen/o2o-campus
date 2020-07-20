package com.siyu.o2o.web.frontend;

import com.siyu.o2o.entity.HeadLine;
import com.siyu.o2o.entity.ShopCategory;
import com.siyu.o2o.service.HeadLineService;
import com.siyu.o2o.service.ShopCategoryService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/frontend"})
public class MainPageController {

   @Autowired
   private ShopCategoryService shopCategoryService;
   @Autowired
   private HeadLineService headLineService;


   @RequestMapping(
      value = {"/listmainpageinfo"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listMainPageInfo() {
      HashMap modelMap = new HashMap();
      new ArrayList();

      try {
         List shopCategoryList = this.shopCategoryService.getShopCategoryList((ShopCategory)null);
         modelMap.put("shopCategoryList", shopCategoryList);
      } catch (Exception var8) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", var8.getMessage());
         return modelMap;
      }

      new ArrayList();

      try {
         HeadLine bestShopList = new HeadLine();
         bestShopList.setEnableStatus(Integer.valueOf(1));
         List headLineList = this.headLineService.getHeadLineList(bestShopList);
         modelMap.put("headLineList", headLineList);
      } catch (Exception var7) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", var7.getMessage());
         return modelMap;
      }

      new ArrayList();

      try {
         List bestShopList1 = this.headLineService.getBestShopList();
         modelMap.put("bestShopList", bestShopList1);
      } catch (Exception var6) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", var6.getMessage());
         return modelMap;
      }

      modelMap.put("success", Boolean.valueOf(true));
      return modelMap;
   }
}
