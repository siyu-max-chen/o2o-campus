package com.siyu.o2o.dto;

import com.siyu.o2o.entity.UserAwardMap;
import com.siyu.o2o.enums.UserAwardMapStateEnum;
import java.util.List;

public class UserAwardMapExecution {

   private int state;
   private String stateInfo;
   private Integer count;
   private UserAwardMap userAwardMap;
   private List userAwardMapList;


   public UserAwardMapExecution() {}

   public UserAwardMapExecution(UserAwardMapStateEnum stateEnum) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
   }

   public UserAwardMapExecution(UserAwardMapStateEnum stateEnum, UserAwardMap userAwardMap) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.userAwardMap = userAwardMap;
   }

   public UserAwardMapExecution(UserAwardMapStateEnum stateEnum, List userAwardMapList) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.userAwardMapList = userAwardMapList;
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

   public UserAwardMap getUserAwardMap() {
      return this.userAwardMap;
   }

   public void setUserAwardMap(UserAwardMap userAwardMap) {
      this.userAwardMap = userAwardMap;
   }

   public List getUserAwardMapList() {
      return this.userAwardMapList;
   }

   public void setUserAwardMapList(List userAwardMapList) {
      this.userAwardMapList = userAwardMapList;
   }
}
