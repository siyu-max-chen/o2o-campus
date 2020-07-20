package com.siyu.o2o.entity;

import com.siyu.o2o.entity.PersonInfo;
import java.util.Date;

public class WechatAuth {

   private Long wechatAuthId;
   private String openId;
   private Date createTime;
   private PersonInfo personInfo;


   public Long getWechatAuthId() {
      return this.wechatAuthId;
   }

   public void setWechatAuthId(Long wechatAuthId) {
      this.wechatAuthId = wechatAuthId;
   }

   public String getOpenId() {
      return this.openId;
   }

   public void setOpenId(String openId) {
      this.openId = openId;
   }

   public Date getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public PersonInfo getPersonInfo() {
      return this.personInfo;
   }

   public void setPersonInfo(PersonInfo personInfo) {
      this.personInfo = personInfo;
   }
}
