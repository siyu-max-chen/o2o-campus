package com.siyu.o2o.entity;

import com.siyu.o2o.entity.Award;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import java.util.Date;

public class UserAwardMap {

   private Long userAwardId;
   private Date createTime;
   private Integer usedStatus;
   private Integer point;
   private PersonInfo user;
   private Award award;
   private Shop shop;
   private PersonInfo operator;


   public Long getUserAwardId() {
      return this.userAwardId;
   }

   public void setUserAwardId(Long userAwardId) {
      this.userAwardId = userAwardId;
   }

   public Date getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public Integer getUsedStatus() {
      return this.usedStatus;
   }

   public void setUsedStatus(Integer usedStatus) {
      this.usedStatus = usedStatus;
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

   public Award getAward() {
      return this.award;
   }

   public void setAward(Award award) {
      this.award = award;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public PersonInfo getOperator() {
      return this.operator;
   }

   public void setOperator(PersonInfo operator) {
      this.operator = operator;
   }
}
