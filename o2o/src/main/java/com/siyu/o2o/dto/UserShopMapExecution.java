package com.siyu.o2o.dto;

import com.siyu.o2o.entity.UserShopMap;
import com.siyu.o2o.enums.UserShopMapStateEnum;
import java.util.List;

public class UserShopMapExecution {

   private int state;
   private String stateInfo;
   private Integer count;
   private UserShopMap userShopMap;
   private List userShopMapList;


   public UserShopMapExecution() {}

   public UserShopMapExecution(UserShopMapStateEnum stateEnum) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
   }

   public UserShopMapExecution(UserShopMapStateEnum stateEnum, UserShopMap userShopMap) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.userShopMap = userShopMap;
   }

   public UserShopMapExecution(UserShopMapStateEnum stateEnum, List userShopMapList) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.userShopMapList = userShopMapList;
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

   public UserShopMap getUserShopMap() {
      return this.userShopMap;
   }

   public void setUserShopMap(UserShopMap userShopMap) {
      this.userShopMap = userShopMap;
   }

   public List getUserShopMapList() {
      return this.userShopMapList;
   }

   public void setUserShopMapList(List userShopMapList) {
      this.userShopMapList = userShopMapList;
   }
}
