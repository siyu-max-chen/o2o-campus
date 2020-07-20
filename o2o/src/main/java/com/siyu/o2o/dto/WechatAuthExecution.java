package com.siyu.o2o.dto;

import com.siyu.o2o.entity.WechatAuth;
import com.siyu.o2o.enums.WechatAuthStateEnum;
import java.util.List;

public class WechatAuthExecution {

   private int state;
   private String stateInfo;
   private int count;
   private WechatAuth wechatAuth;
   private List wechatAuthList;


   public WechatAuthExecution() {}

   public WechatAuthExecution(WechatAuthStateEnum stateEnum) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
   }

   public WechatAuthExecution(WechatAuthStateEnum stateEnum, WechatAuth wechatAuth) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.wechatAuth = wechatAuth;
   }

   public WechatAuthExecution(WechatAuthStateEnum stateEnum, List wechatAuthList) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.wechatAuthList = wechatAuthList;
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

   public WechatAuth getWechatAuth() {
      return this.wechatAuth;
   }

   public void setWechatAuth(WechatAuth wechatAuth) {
      this.wechatAuth = wechatAuth;
   }

   public List getWechatAuthList() {
      return this.wechatAuthList;
   }

   public void setWechatAuthList(List wechatAuthList) {
      this.wechatAuthList = wechatAuthList;
   }
}
