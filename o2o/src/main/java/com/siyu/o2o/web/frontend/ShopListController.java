package com.siyu.o2o.web.frontend;

import com.siyu.o2o.dto.ShopExecution;
import com.siyu.o2o.entity.Area;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.ShopCategory;
import com.siyu.o2o.service.AreaService;
import com.siyu.o2o.service.ShopCategoryService;
import com.siyu.o2o.service.ShopService;
import com.siyu.o2o.util.HttpServletRequestUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/frontend"})
public class ShopListController {

   @Autowired
   private AreaService areaService;
   @Autowired
   private ShopCategoryService shopCategoryService;
   @Autowired
   private ShopService shopService;


   @RequestMapping(
      value = {"/listshopspageinfo"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listShopsPageInfo(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      long parentId = HttpServletRequestUtil.getLong(request, "parentId");
      List shopCategoryList = null;
      ShopCategory areaList;
      if(parentId != -1L) {
         try {
            areaList = new ShopCategory();
            ShopCategory e = new ShopCategory();
            e.setShopCategoryId(Long.valueOf(parentId));
            areaList.setParent(e);
            shopCategoryList = this.shopCategoryService.getShopCategoryList(areaList);
         } catch (Exception var10) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var10.getMessage());
         }
      } else {
         try {
            shopCategoryList = this.shopCategoryService.getShopCategoryList((ShopCategory)null);
         } catch (Exception var9) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var9.getMessage());
         }
      }

      modelMap.put("shopCategoryList", shopCategoryList);
      areaList = null;

      try {
         List areaList1 = this.areaService.getAreaList();
         modelMap.put("areaList", areaList1);
         modelMap.put("success", Boolean.valueOf(true));
         return modelMap;
      } catch (Exception var8) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", var8.getMessage());
         return modelMap;
      }
   }

   @RequestMapping(
      value = {"/listshops"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listShops(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      if(pageIndex > -1 && pageSize > -1) {
         long parentId = HttpServletRequestUtil.getLong(request, "parentId");
         long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
         int areaId = HttpServletRequestUtil.getInt(request, "areaId");
         String shopName = HttpServletRequestUtil.getString(request, "shopName");
         Shop shopCondition = this.compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
         ShopExecution se = this.shopService.getShopList(shopCondition, pageIndex, pageSize);
         modelMap.put("shopList", se.getShopList());
         modelMap.put("count", Integer.valueOf(se.getCount()));
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/getshopbyid"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getShopById(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      Long shopId = Long.valueOf(HttpServletRequestUtil.getLong(request, "shopId"));
      if(shopId.longValue() > -1L) {
         try {
            Shop e = this.shopService.getByShopId(shopId.longValue());
            List areaList = this.areaService.getAreaList();
            modelMap.put("shop", e);
            modelMap.put("areaList", areaList);
            modelMap.put("success", Boolean.valueOf(true));
         } catch (Exception var6) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var6.toString());
         }
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty shopId");
      }

      return modelMap;
   }

   private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
      Shop shopCondition = new Shop();
      ShopCategory area;
      if(parentId != -1L) {
         area = new ShopCategory();
         ShopCategory parentCategory = new ShopCategory();
         parentCategory.setShopCategoryId(Long.valueOf(parentId));
         area.setParent(parentCategory);
         shopCondition.setShopCategory(area);
      }

      if(shopCategoryId != -1L) {
         area = new ShopCategory();
         area.setShopCategoryId(Long.valueOf(shopCategoryId));
         shopCondition.setShopCategory(area);
      }

      if((long)areaId != -1L) {
         Area area1 = new Area();
         area1.setAreaId(Integer.valueOf(areaId));
         shopCondition.setArea(area1);
      }

      if(shopName != null) {
         shopCondition.setShopName(shopName);
      }

      shopCondition.setEnableStatus(Integer.valueOf(1));
      return shopCondition;
   }
}
