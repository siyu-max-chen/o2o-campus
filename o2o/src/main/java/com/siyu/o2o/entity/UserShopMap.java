package com.siyu.o2o.entity;

import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import java.util.Date;

public class UserShopMap {

   private Long userShopId;
   private Date createTime;
   private Integer point;
   private PersonInfo user;
   private Shop shop;


   public Long getUserShopId() {
      return this.userShopId;
   }

   public void setUserShopId(Long userShopId) {
      this.userShopId = userShopId;
   }

   public Date getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public Integer getPoint() {
      return this.point;
   }

   public void setPoint(Integer point) {
      this.point = point;
   }

   public PersonInfo getUser() {
      return this.user;
   }

   public void setUser(PersonInfo user) {
      this.user = user;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }
}
