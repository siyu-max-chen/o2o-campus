package com.siyu.o2o.web.shopadmin;

import com.siyu.o2o.dto.ProductCategoryExecution;
import com.siyu.o2o.dto.Result;
import com.siyu.o2o.entity.ProductCategory;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.enums.ProductCategoryStateEnum;
import com.siyu.o2o.exceptions.ProductCategoryOperationException;
import com.siyu.o2o.service.ProductCategoryService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/shopadmin"})
public class ProductCategoryManagementController {

   @Autowired
   private ProductCategoryService productCategoryService;


   @RequestMapping(
      value = {"/getproductcategorylist"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Result getProductCategoryList(HttpServletRequest request) {
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      List list = null;
      if(currentShop != null && currentShop.getShopId().longValue() > 0L) {
         list = this.productCategoryService.getProductCategoryList(currentShop.getShopId().longValue());
         return new Result(true, list);
      } else {
         ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
         return new Result(false, ps.getState(), ps.getStateInfo());
      }
   }

   @RequestMapping(
      value = {"/addproductcategorys"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map addProductCategorys(@RequestBody List productCategoryList, HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      Iterator e = productCategoryList.iterator();

      while(e.hasNext()) {
         ProductCategory pc = (ProductCategory)e.next();
         pc.setShopId(currentShop.getShopId());
      }

      if(productCategoryList != null && productCategoryList.size() > 0) {
         try {
            ProductCategoryExecution e1 = this.productCategoryService.batchAddProductCategory(productCategoryList);
            if(e1.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
               modelMap.put("success", Boolean.valueOf(true));
            } else {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", e1.getStateInfo());
            }
         } catch (ProductCategoryOperationException var7) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var7.toString());
            return modelMap;
         }
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "请至少输入一个商品类别");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/removeproductcategory"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map removeProductCategory(Long productCategoryId, HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      if(productCategoryId != null && productCategoryId.longValue() > 0L) {
         try {
            Shop e = (Shop)request.getSession().getAttribute("currentShop");
            ProductCategoryExecution pe = this.productCategoryService.deleteProductCategory(productCategoryId.longValue(), e.getShopId().longValue());
            if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
               modelMap.put("success", Boolean.valueOf(true));
            } else {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", pe.getStateInfo());
            }
         } catch (ProductCategoryOperationException var6) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var6.toString());
            return modelMap;
         }
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "请至少选择一个商品类别");
      }

      return modelMap;
   }
}
