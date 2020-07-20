package com.siyu.o2o.entity;

import java.util.Date;

public class ProductImg {

   private Long productImgId;
   private String imgAddr;
   private String imgDesc;
   private Integer priority;
   private Date createTime;
   private Long productId;


   public Long getProductImgId() {
      return this.productImgId;
   }

   public void setProductImgId(Long productImgId) {
      this.productImgId = productImgId;
   }

   public String getImgAddr() {
      return this.imgAddr;
   }

   public void setImgAddr(String imgAddr) {
      this.imgAddr = imgAddr;
   }

   public String getImgDesc() {
      return this.imgDesc;
   }

   public void setImgDesc(String imgDesc) {
      this.imgDesc = imgDesc;
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

   public Long getProductId() {
      return this.productId;
   }

   public void setProductId(Long productId) {
      this.productId = productId;
   }
}
