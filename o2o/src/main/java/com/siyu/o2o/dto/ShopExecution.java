package com.siyu.o2o.dto;

import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.enums.ShopStateEnum;
import java.util.List;

public class ShopExecution {

   private int state;
   private String stateInfo;
   private int count;
   private Shop shop;
   private List shopList;


   public ShopExecution() {}

   public ShopExecution(ShopStateEnum stateEnum) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
   }

   public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.shop = shop;
   }

   public ShopExecution(ShopStateEnum stateEnum, List shopList) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.shopList = shopList;
   }

   public int getState() {
      return this.state;
   }

   public void setState(int state) {
      this.state = state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public void setStateInfo(String stateInfo) {
      this.stateInfo = stateInfo;
   }

   public int getCount() {
      return this.count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public List getShopList() {
      return this.shopList;
   }

   public void setShopList(List shopList) {
      this.shopList = shopList;
   }
}
