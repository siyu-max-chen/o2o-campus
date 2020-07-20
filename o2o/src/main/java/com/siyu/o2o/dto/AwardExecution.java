package com.siyu.o2o.dto;

import com.siyu.o2o.entity.Award;
import com.siyu.o2o.enums.AwardStateEnum;
import java.util.List;

public class AwardExecution {

   private int state;
   private String stateInfo;
   private int count;
   private Award award;
   private List awardList;


   public AwardExecution() {}

   public AwardExecution(AwardStateEnum stateEnum) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
   }

   public AwardExecution(AwardStateEnum stateEnum, Award award) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.award = award;
   }

   public AwardExecution(AwardStateEnum stateEnum, List awardList) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.awardList = awardList;
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

   public Award getAward() {
      return this.award;
   }

   public void setAward(Award award) {
      this.award = award;
   }

   public List getAwardList() {
      return this.awardList;
   }

   public void setAwardList(List awardList) {
      this.awardList = awardList;
   }
}
