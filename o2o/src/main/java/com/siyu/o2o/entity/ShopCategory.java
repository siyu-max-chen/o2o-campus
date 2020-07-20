package com.siyu.o2o.entity;

import java.util.Date;

public class ShopCategory {

   private Long shopCategoryId;
   private String shopCategoryName;
   private String shopCategoryDesc;
   private String shopCategoryImg;
   private Integer priority;
   private Date createTime;
   private Date lastEditTime;
   private ShopCategory parent;


   public Long getShopCategoryId() {
      return this.shopCategoryId;
   }

   public void setShopCategoryId(Long shopCategoryId) {
      this.shopCategoryId = shopCategoryId;
   }

   public String getShopCategoryName() {
      return this.shopCategoryName;
   }

   public void setShopCategoryName(String shopCategoryName) {
      this.shopCategoryName = shopCategoryName;
   }

   public String getShopCategoryDesc() {
      return this.shopCategoryDesc;
   }

   public void setShopCategoryDesc(String shopCategoryDesc) {
      this.shopCategoryDesc = shopCategoryDesc;
   }

   public String getShopCategoryImg() {
      return this.shopCategoryImg;
   }

   public void setShopCategoryImg(String shopCategoryImg) {
      this.shopCategoryImg = shopCategoryImg;
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

   public ShopCategory getParent() {
      return this.parent;
   }

   public void setParent(ShopCategory parent) {
      this.parent = parent;
   }
}
