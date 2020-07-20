package com.siyu.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.dto.ShopExecution;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.ShopCategory;
import com.siyu.o2o.enums.ShopStateEnum;
import com.siyu.o2o.exceptions.ShopOperationException;
import com.siyu.o2o.service.AreaService;
import com.siyu.o2o.service.ShopCategoryService;
import com.siyu.o2o.service.ShopService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping({"/shopadmin"})
public class ShopManagementController {

   @Autowired
   private ShopService shopService;
   @Autowired
   private ShopCategoryService shopCategoryService;
   @Autowired
   private AreaService areaService;


   @RequestMapping(
      value = {"/getshopmanagementinfo"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getShopManagementInfo(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      long shopId = HttpServletRequestUtil.getLong(request, "shopId");
      if(shopId <= 0L) {
         Object currentShop = request.getSession().getAttribute("currentShop");
         if(currentShop == null) {
            modelMap.put("redirect", Boolean.valueOf(true));
            modelMap.put("url", "/o2o/shopadmin/shoplist");
         } else {
            Shop currentShop1 = (Shop)currentShop;
            modelMap.put("redirect", Boolean.valueOf(false));
            modelMap.put("shopId", currentShop1.getShopId());
         }
      } else {
         Shop currentShop2 = new Shop();
         currentShop2.setShopId(Long.valueOf(shopId));
         request.getSession().setAttribute("currentShop", currentShop2);
         modelMap.put("redirect", Boolean.valueOf(false));
         modelMap.put("shopId", Long.valueOf(shopId));
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/getshoplist"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getShopList(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");

      try {
         Shop e = new Shop();
         e.setOwner(user);
         ShopExecution se = this.shopService.getShopList(e, 0, 100);
         modelMap.put("shopList", se.getShopList());
         request.getSession().setAttribute("shopList", se.getShopList());
         modelMap.put("user", user);
         modelMap.put("success", Boolean.valueOf(true));
      } catch (Exception var6) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", var6.getMessage());
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

   @RequestMapping(
      value = {"/getshopinitinfo"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getShopInitInfo() {
      HashMap modelMap = new HashMap();
      new ArrayList();
      new ArrayList();

      try {
         List shopCategoryList = this.shopCategoryService.getShopCategoryList(new ShopCategory());
         List areaList = this.areaService.getAreaList();
         modelMap.put("shopCategoryList", shopCategoryList);
         modelMap.put("areaList", areaList);
         modelMap.put("success", Boolean.valueOf(true));
      } catch (Exception var5) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", var5.getMessage());
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/registershop"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map registerShop(HttpServletRequest request) throws IOException {
      HashMap modelMap = new HashMap();
      if(!CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
         ObjectMapper mapper = new ObjectMapper();
         Shop shop = null;

         try {
            shop = (Shop)mapper.readValue(shopStr, Shop.class);
         } catch (Exception var12) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var12.getMessage());
            return modelMap;
         }

         CommonsMultipartFile shopImg = null;
         CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
         if(!commonsMultipartResolver.isMultipart(request)) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
         } else {
            MultipartHttpServletRequest owner = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)owner.getFile("shopImg");
            if(shop != null && shopImg != null) {
               PersonInfo owner1 = (PersonInfo)request.getSession().getAttribute("user");
               shop.setOwner(owner1);
               ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
               ShopExecution se = this.shopService.addShop(shop, imageHolder);
               if(se.getState() == ShopStateEnum.CHECK.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
                  Object shopList = (List)request.getSession().getAttribute("shopList");
                  if(shopList == null || ((List)shopList).size() == 0) {
                     shopList = new ArrayList();
                  }

                  ((List)shopList).add(se.getShop());
                  request.getSession().setAttribute("shopList", shopList);
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", se.getStateInfo());
               }

               return modelMap;
            } else {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", "请输入店铺信息");
               return modelMap;
            }
         }
      }
   }

   @RequestMapping(
      value = {"/modifyshop"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map modifyShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      if(!CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
         ObjectMapper mapper = new ObjectMapper();
         Shop shop = null;

         try {
            shop = (Shop)mapper.readValue(shopStr, Shop.class);
         } catch (Exception var12) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var12.getMessage());
            return modelMap;
         }

         CommonsMultipartFile shopImg = null;
         CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
         if(commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest se = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)se.getFile("shopImg");
         }

         if(shop != null && shop.getShopId() != null) {
            try {
               ShopExecution se1;
               if(shopImg == null) {
                  se1 = this.shopService.modifyShop(shop, (ImageHolder)null);
               } else {
                  ImageHolder e = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                  se1 = this.shopService.modifyShop(shop, e);
               }

               if(se1.getState() == ShopStateEnum.SUCCESS.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", se1.getStateInfo());
               }
            } catch (ShopOperationException var10) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var10.getMessage());
            } catch (IOException var11) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var11.getMessage());
            }

            return modelMap;
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "请输入店铺Id");
            return modelMap;
         }
      }
   }
}
