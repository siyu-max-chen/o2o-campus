package com.siyu.o2o.dto;


public class WechatInfo {

   private Long customerId;
   private Long productId;
   private Long userAwardId;
   private Long createTime;
   private Long shopId;


   public Long getCustomerId() {
      return this.customerId;
   }

   public void setCustomerId(Long customerId) {
      this.customerId = customerId;
   }

   public Long getProductId() {
      return this.productId;
   }

   public void setProductId(Long productId) {
      this.productId = productId;
   }

   public Long getUserAwardId() {
      return this.userAwardId;
   }

   public void setUserAwardId(Long userAwardId) {
      this.userAwardId = userAwardId;
   }

   public Long getShopId() {
      return this.shopId;
   }

   public void setShopId(Long shopId) {
      this.shopId = shopId;
   }

   public Long getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Long createTime) {
      this.createTime = createTime;
   }
}
