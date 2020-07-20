package com.siyu.o2o.dto;

import com.siyu.o2o.entity.UserProductMap;
import com.siyu.o2o.enums.UserProductMapStateEnum;
import java.util.List;

public class UserProductMapExecution {

   private int state;
   private String stateInfo;
   private Integer count;
   private UserProductMap userProductMap;
   private List userProductMapList;


   public UserProductMapExecution() {}

   public UserProductMapExecution(UserProductMapStateEnum stateEnum) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
   }

   public UserProductMapExecution(UserProductMapStateEnum stateEnum, UserProductMap userProductMap) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.userProductMap = userProductMap;
   }

   public UserProductMapExecution(UserProductMapStateEnum stateEnum, List userProductMapList) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.userProductMapList = userProductMapList;
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

   public UserProductMap getUserProductMap() {
      return this.userProductMap;
   }

   public void setUserProductMap(UserProductMap userProductMap) {
      this.userProductMap = userProductMap;
   }

   public List getUserProductMapList() {
      return this.userProductMapList;
   }

   public void setUserProductMapList(List userProductMapList) {
      this.userProductMapList = userProductMapList;
   }
}
