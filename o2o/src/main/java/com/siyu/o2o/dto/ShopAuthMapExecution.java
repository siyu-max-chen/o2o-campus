package com.siyu.o2o.dto;

import com.siyu.o2o.entity.ShopAuthMap;
import com.siyu.o2o.enums.ShopAuthMapStateEnum;
import java.util.List;

public class ShopAuthMapExecution {

   private int state;
   private String stateInfo;
   private Integer count;
   private ShopAuthMap shopAuthMap;
   private List shopAuthMapList;


   public ShopAuthMapExecution() {}

   public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
   }

   public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum, ShopAuthMap shopAuthMap) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.shopAuthMap = shopAuthMap;
   }

   public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum, List shopAuthMapList) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.shopAuthMapList = shopAuthMapList;
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

   public Integer getCount() {
      return this.count;
   }

   public void setCount(Integer count) {
      this.count = count;
   }

   public ShopAuthMap getShopAuthMap() {
      return this.shopAuthMap;
   }

   public void setShopAuthMap(ShopAuthMap shopAuthMap) {
      this.shopAuthMap = shopAuthMap;
   }

   public List getShopAuthMapList() {
      return this.shopAuthMapList;
   }

   public void setShopAuthMapList(List shopAuthMapList) {
      this.shopAuthMapList = shopAuthMapList;
   }
}
