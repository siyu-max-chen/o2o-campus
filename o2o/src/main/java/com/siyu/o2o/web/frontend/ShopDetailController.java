package com.siyu.o2o.web.frontend;

import com.siyu.o2o.dao.ShopDao;
import com.siyu.o2o.dao.UserShopLikeDao;
import com.siyu.o2o.dto.ProductExecution;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.entity.ProductCategory;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.UserShopLikeMap;
import com.siyu.o2o.service.ProductCategoryService;
import com.siyu.o2o.service.ProductService;
import com.siyu.o2o.service.ShopService;
import com.siyu.o2o.util.HttpServletRequestUtil;
import java.util.Date;
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
public class ShopDetailController {

   @Autowired
   private ShopService shopService;
   @Autowired
   private ProductService productService;
   @Autowired
   private ProductCategoryService productCategoryService;
   @Autowired
   private UserShopLikeDao userShopLikeDao;
   @Autowired
   private ShopDao shopDao;


   @RequestMapping(
      value = {"/listshopdetailpageinfo"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listShopDetailPageInfo(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      long shopId = HttpServletRequestUtil.getLong(request, "shopId");
      Shop shop = null;
      List productCategoryList = null;
      if(shopId != -1L) {
         shop = this.shopService.getByShopId(shopId);
         productCategoryList = this.productCategoryService.getProductCategoryList(shopId);
         modelMap.put("shop", shop);
         modelMap.put("productCategoryList", productCategoryList);
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty shopId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/listproductsbyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listProductsByShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      long shopId = HttpServletRequestUtil.getLong(request, "shopId");
      if(pageIndex > -1 && pageSize > -1 && shopId > -1L) {
         long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
         String productName = HttpServletRequestUtil.getString(request, "productName");
         Product productCondition = this.compactProductCondition4Search(shopId, productCategoryId, productName);
         ProductExecution pe = this.productService.getProductList(productCondition, pageIndex, pageSize);
         modelMap.put("productList", pe.getProductList());
         modelMap.put("count", Integer.valueOf(pe.getCount()));
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
      }

      return modelMap;
   }

   private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
      Product productCondition = new Product();
      Shop shop = new Shop();
      shop.setShopId(Long.valueOf(shopId));
      productCondition.setShop(shop);
      if(productCategoryId != -1L) {
         ProductCategory productCategory = new ProductCategory();
         productCategory.setProductCategoryId(Long.valueOf(productCategoryId));
         productCondition.setProductCategory(productCategory);
      }

      if(productName != null) {
         productCondition.setProductName(productName);
      }

      productCondition.setEnableStatus(Integer.valueOf(1));
      return productCondition;
   }

   @RequestMapping(
      value = {"/checkuserlikeshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map checkuserlikeshop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      long userId = (long)HttpServletRequestUtil.getInt(request, "userId");
      long shopId = (long)HttpServletRequestUtil.getInt(request, "shopId");
      if(userId > -1L && shopId > -1L) {
         UserShopLikeMap likeMap = this.userShopLikeDao.queryByUserIdShopId(userId, shopId);
         if(likeMap != null) {
            modelMap.put("success", Boolean.valueOf(true));
            modelMap.put("existed", Boolean.valueOf(true));
            modelMap.put("likeMap", likeMap);
         } else {
            modelMap.put("success", Boolean.valueOf(true));
            modelMap.put("existed", Boolean.valueOf(false));
         }
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "user not logged in or shop not existed!");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/toggleuserlikeshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map toggleuserlikeshop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      long userId = (long)HttpServletRequestUtil.getInt(request, "userId");
      long shopId = (long)HttpServletRequestUtil.getInt(request, "shopId");
      boolean liked = HttpServletRequestUtil.getBoolean(request, "liked");
      System.out.println("*****************");
      System.out.println(userId + " " + shopId + " " + liked);
      if(userId > -1L && shopId > -1L) {
         Shop e;
         UserShopLikeMap likeMap;
         if(liked) {
            try {
               e = this.shopDao.queryByShopId(shopId);
               likeMap = this.userShopLikeDao.queryByUserIdShopId(userId, shopId);
               if(e != null && likeMap != null) {
                  Date shopCondition = new Date();
                  if(likeMap.getLastEditTime() != null && shopCondition.getTime() - likeMap.getLastEditTime().getTime() < 10000L) {
                     modelMap.put("success", Boolean.valueOf(false));
                     modelMap.put("errMsg", "Operation cooling down for 10 seconds!");
                  } else {
                     Shop shopCondition1 = new Shop();
                     shopCondition1.setShopId(Long.valueOf(shopId));
                     shopCondition1.setLikes(Long.valueOf(e.getLikes().longValue() - 1L));
                     this.shopDao.updateShop(shopCondition1);
                     this.userShopLikeDao.deleteUserShopLike(userId, shopId);
                     modelMap.put("success", Boolean.valueOf(true));
                     modelMap.put("liked", Boolean.valueOf(false));
                     modelMap.put("likeMap", (Object)null);
                     modelMap.put("likeCount", shopCondition1.getLikes());
                  }
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", "Something go wrong here!");
               }
            } catch (Exception var13) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", "Something go wrong here! " + var13.getMessage());
            }
         } else {
            try {
               e = this.shopDao.queryByShopId(shopId);
               if(e != null) {
                  likeMap = new UserShopLikeMap();
                  likeMap.setShopId(Long.valueOf(shopId));
                  likeMap.setUserId(Long.valueOf(userId));
                  likeMap.setLastEditTime(new Date());
                  Shop shopCondition2 = new Shop();
                  shopCondition2.setShopId(Long.valueOf(shopId));
                  shopCondition2.setLikes(Long.valueOf(e.getLikes().longValue() + 1L));
                  this.shopDao.updateShop(shopCondition2);
                  this.userShopLikeDao.insertUserShopLike(likeMap);
                  modelMap.put("success", Boolean.valueOf(true));
                  modelMap.put("liked", Boolean.valueOf(true));
                  modelMap.put("likeMap", likeMap);
                  modelMap.put("likeCount", shopCondition2.getLikes());
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", "Something go wrong here!");
               }
            } catch (Exception var12) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", "Something go wrong here! " + var12.getMessage());
            }
         }
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "user not logged in or shop not existed!");
      }

      return modelMap;
   }
}
