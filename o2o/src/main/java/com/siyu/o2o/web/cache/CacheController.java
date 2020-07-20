package com.siyu.o2o.web.cache;

import com.siyu.o2o.service.AreaService;
import com.siyu.o2o.service.CacheService;
import com.siyu.o2o.service.HeadLineService;
import com.siyu.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CacheController {

   @Autowired
   private CacheService cacheService;
   @Autowired
   private AreaService areaService;
   @Autowired
   private HeadLineService headLineService;
   @Autowired
   private ShopCategoryService shopCategoryService;


   @RequestMapping(
      value = {"/clearcache4area"},
      method = {RequestMethod.GET}
   )
   private String clearCache4Area() {
      AreaService var10001 = this.areaService;
      this.cacheService.removeFromCache("arealist");
      return "shop/operationsuccess";
   }

   @RequestMapping(
      value = {"/clearcache4headline"},
      method = {RequestMethod.GET}
   )
   private String clearCache4Headline() {
      HeadLineService var10001 = this.headLineService;
      this.cacheService.removeFromCache("headlinelist");
      return "shop/operationsuccess";
   }

   @RequestMapping(
      value = {"/clearcache4shopcategory"},
      method = {RequestMethod.GET}
   )
   private String clearCache4ShopCategory() {
      ShopCategoryService var10001 = this.shopCategoryService;
      this.cacheService.removeFromCache("shopcategorylist");
      return "shop/operationsuccess";
   }
}
