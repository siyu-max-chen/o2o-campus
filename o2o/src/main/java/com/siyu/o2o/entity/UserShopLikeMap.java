package com.siyu.o2o.entity;

import java.util.Date;

public class UserShopLikeMap {

   private Long userShopLikeId;
   private Date lastEditTime;
   private Long userId;
   private Long shopId;


   public Long getUserShopLikeId() {
      return this.userShopLikeId;
   }

   public void setUserShopLikeId(Long userShopLikeId) {
      this.userShopLikeId = userShopLikeId;
   }

   public Date getLastEditTime() {
      return this.lastEditTime;
   }

   public void setLastEditTime(Date lastEditTime) {
      this.lastEditTime = lastEditTime;
   }

   public Long getUserId() {
      return this.userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public Long getShopId() {
      return this.shopId;
   }

   public void setShopId(Long shopId) {
      this.shopId = shopId;
   }
}
