package com.siyu.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(
   value = {"shopadmin"},
   method = {RequestMethod.GET}
)
public class ShopAdminController {

   @RequestMapping({"/shopoperation"})
   public String shopOperation() {
      return "shop/shopoperation";
   }

   @RequestMapping({"/shoplist"})
   public String shopList() {
      return "shop/shoplist";
   }

   @RequestMapping({"/shopmanagement"})
   public String shopManagement() {
      return "shop/shopmanagement";
   }

   @RequestMapping(
      value = {"/productcategorymanagement"},
      method = {RequestMethod.GET}
   )
   private String productCategoryManage() {
      return "shop/productcategorymanagement";
   }

   @RequestMapping({"/productoperation"})
   public String productOperation() {
      return "shop/productoperation";
   }

   @RequestMapping({"/productmanagement"})
   public String productManagement() {
      return "shop/productmanagement";
   }

   @RequestMapping({"/shopauthmanagement"})
   public String shopAuthManagement() {
      return "shop/shopauthmanagement";
   }

   @RequestMapping({"/shopauthedit"})
   public String shopAuthEdit() {
      return "shop/shopauthedit";
   }

   @RequestMapping(
      value = {"/operationsuccess"},
      method = {RequestMethod.GET}
   )
   private String operationSuccess() {
      return "shop/operationsuccess";
   }

   @RequestMapping(
      value = {"/operationfail"},
      method = {RequestMethod.GET}
   )
   private String operationFail() {
      return "shop/operationfail";
   }

   @RequestMapping(
      value = {"/productbuycheck"},
      method = {RequestMethod.GET}
   )
   private String productBuyCheck() {
      return "shop/productbuycheck";
   }

   @RequestMapping(
      value = {"/awardmanagement"},
      method = {RequestMethod.GET}
   )
   private String awardManagement() {
      return "shop/awardmanagement";
   }

   @RequestMapping(
      value = {"/awardoperation"},
      method = {RequestMethod.GET}
   )
   private String awardEdit() {
      return "shop/awardoperation";
   }

   @RequestMapping(
      value = {"/usershopcheck"},
      method = {RequestMethod.GET}
   )
   private String userShopCheck() {
      return "shop/usershopcheck";
   }

   @RequestMapping(
      value = {"/awarddelivercheck"},
      method = {RequestMethod.GET}
   )
   private String awardDeliverCheck() {
      return "shop/awarddelivercheck";
   }
}
