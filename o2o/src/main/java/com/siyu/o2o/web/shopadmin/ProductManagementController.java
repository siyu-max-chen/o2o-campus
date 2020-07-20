package com.siyu.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.dto.ProductExecution;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.entity.ProductCategory;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.enums.ProductStateEnum;
import com.siyu.o2o.exceptions.ProductOperationException;
import com.siyu.o2o.service.ProductCategoryService;
import com.siyu.o2o.service.ProductService;
import com.siyu.o2o.util.CodeUtil;
import com.siyu.o2o.util.HttpServletRequestUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping({"/shopadmin"})
public class ProductManagementController {

   @Autowired
   private ProductService productService;
   @Autowired
   private ProductCategoryService productCategoryService;
   private static final int IMAGEMAXCOUNT = 6;


   @RequestMapping(
      value = {"/getproductlistbyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getProductListByShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      if(pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
         long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
         String productName = HttpServletRequestUtil.getString(request, "productName");
         Product productCondition = this.compactProductCondition(currentShop.getShopId().longValue(), productCategoryId, productName);
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

   @RequestMapping(
      value = {"/getproductbyid"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getProductById(@RequestParam Long productId) {
      HashMap modelMap = new HashMap();
      if(productId.longValue() > -1L) {
         Product product = this.productService.getProductById(productId.longValue());
         List productCategoryList = this.productCategoryService.getProductCategoryList(product.getShop().getShopId().longValue());
         modelMap.put("product", product);
         modelMap.put("productCategoryList", productCategoryList);
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty productId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/addproduct"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map addProduct(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      if(!CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         ObjectMapper mapper = new ObjectMapper();
         Product product = null;
         ImageHolder thumbnail = null;
         ArrayList productImgList = new ArrayList();
         CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

         try {
            if(!multipartResolver.isMultipart(request)) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", "上传图片不能为空");
               return modelMap;
            }

            thumbnail = this.handleImage(request, thumbnail, productImgList);
         } catch (Exception var12) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var12.toString());
            return modelMap;
         }

         try {
            String e = HttpServletRequestUtil.getString(request, "productStr");
            product = (Product)mapper.readValue(e, Product.class);
         } catch (Exception var11) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var11.toString());
            return modelMap;
         }

         if(product != null && thumbnail != null && productImgList.size() > 0) {
            try {
               Shop e1 = (Shop)request.getSession().getAttribute("currentShop");
               product.setShop(e1);
               ProductExecution pe = this.productService.addProduct(product, thumbnail, productImgList);
               if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", pe.getStateInfo());
               }
            } catch (ProductOperationException var10) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var10.toString());
               return modelMap;
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "请输入商品信息");
            modelMap.put("errMsgDetail", (product != null) + "," + (thumbnail != null) + "," + productImgList.size());
         }

         return modelMap;
      }
   }

   private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List productImgList) throws IOException {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
      CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
      if(thumbnailFile != null) {
         thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
      }

      for(int i = 0; i < 6; ++i) {
         CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" + i);
         if(productImgFile == null) {
            break;
         }

         ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
         productImgList.add(productImg);
      }

      return thumbnail;
   }

   @RequestMapping(
      value = {"/modifyproduct"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map modifyProduct(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
      if(!statusChange && !CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         ObjectMapper mapper = new ObjectMapper();
         Product product = null;
         ImageHolder thumbnail = null;
         ArrayList productImgList = new ArrayList();
         CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

         try {
            if(multipartResolver.isMultipart(request)) {
               thumbnail = this.handleImage(request, thumbnail, productImgList);
            }
         } catch (Exception var13) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var13.toString());
            return modelMap;
         }

         try {
            String e = HttpServletRequestUtil.getString(request, "productStr");
            product = (Product)mapper.readValue(e, Product.class);
         } catch (Exception var12) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var12.toString());
            return modelMap;
         }

         if(product != null) {
            try {
               Shop e1 = (Shop)request.getSession().getAttribute("currentShop");
               product.setShop(e1);
               ProductExecution pe = this.productService.modifyProduct(product, thumbnail, productImgList);
               if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", pe.getStateInfo());
               }
            } catch (RuntimeException var11) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var11.toString());
               return modelMap;
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "请输入商品信息");
         }

         return modelMap;
      }
   }

   private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
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

      return productCondition;
   }
}
