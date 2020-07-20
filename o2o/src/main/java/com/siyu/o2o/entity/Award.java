package com.siyu.o2o.entity;

import java.util.Date;

public class Award {

   private Long awardId;
   private String awardName;
   private String awardDesc;
   private String awardImg;
   private Integer point;
   private Integer priority;
   private Date createTime;
   private Date lastEditTime;
   private Integer enableStatus;
   private Long shopId;


   public Long getAwardId() {
      return this.awardId;
   }

   public void setAwardId(Long awardId) {
      this.awardId = awardId;
   }

   public String getAwardName() {
      return this.awardName;
   }

   public void setAwardName(String awardName) {
      this.awardName = awardName;
   }

   public String getAwardDesc() {
      return this.awardDesc;
   }

   public void setAwardDesc(String awardDesc) {
      this.awardDesc = awardDesc;
   }

   public String getAwardImg() {
      return this.awardImg;
   }

   public void setAwardImg(String awardImg) {
      this.awardImg = awardImg;
   }

   public Integer getPoint() {
      return this.point;
   }

   public void setPoint(Integer point) {
      this.point = point;
   }

   public Integer getPriority() {
      return this.priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
   }

   public Date getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public Date getLastEditTime() {
      return this.lastEditTime;
   }

   public void setLastEditTime(Date lastEditTime) {
      this.lastEditTime = lastEditTime;
   }

   public Integer getEnableStatus() {
      return this.enableStatus;
   }

   public void setEnableStatus(Integer enableStatus) {
      this.enableStatus = enableStatus;
   }

   public Long getShopId() {
      return this.shopId;
   }

   public void setShopId(Long shopId) {
      this.shopId = shopId;
   }
}
