package com.siyu.o2o.entity;

import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.entity.Shop;
import java.util.Date;

public class UserProductMap {

   private Long userProductId;
   private Date createTime;
   private Integer point;
   private PersonInfo user;
   private Product product;
   private Shop shop;
   private PersonInfo operator;


   public Long getUserProductId() {
      return this.userProductId;
   }

   public void setUserProductId(Long userProductId) {
      this.userProductId = userProductId;
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

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
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
