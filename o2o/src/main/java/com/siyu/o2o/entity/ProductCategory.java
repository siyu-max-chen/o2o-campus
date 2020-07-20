package com.siyu.o2o.entity;

import java.util.Date;

public class ProductCategory {

   private Long productCategoryId;
   private Long shopId;
   private String productCategoryName;
   private Integer priority;
   private Date createTime;


   public Long getProductCategoryId() {
      return this.productCategoryId;
   }

   public void setProductCategoryId(Long productCategoryId) {
      this.productCategoryId = productCategoryId;
   }

   public Long getShopId() {
      return this.shopId;
   }

   public void setShopId(Long shopId) {
      this.shopId = shopId;
   }

   public String getProductCategoryName() {
      return this.productCategoryName;
   }

   public void setProductCategoryName(String productCategoryName) {
      this.productCategoryName = productCategoryName;
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
}
