package com.siyu.o2o.entity;

import com.siyu.o2o.entity.Area;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.ShopCategory;
import java.util.Date;

public class Shop {

   private Long shopId;
   private String shopName;
   private String shopDesc;
   private String shopAddr;
   private String phone;
   private String shopImg;
   private Integer priority;
   private Date createTime;
   private Date lastEditTime;
   private Integer enableStatus;
   private String advice;
   private Area area;
   private PersonInfo owner;
   private ShopCategory shopCategory;
   private Long likes;
   private Integer dollar;
   private Long ratingSum;
   private Long ratingCount;


   public Long getLikes() {
      return this.likes;
   }

   public void setLikes(Long likes) {
      this.likes = likes;
   }

   public Integer getDollar() {
      return this.dollar;
   }

   public void setDollar(Integer dollar) {
      this.dollar = dollar;
   }

   public Long getRatingSum() {
      return this.ratingSum;
   }

   public void setRatingSum(Long ratingSum) {
      this.ratingSum = ratingSum;
   }

   public Long getRatingCount() {
      return this.ratingCount;
   }

   public void setRatingCount(Long ratingCount) {
      this.ratingCount = ratingCount;
   }

   public Long getShopId() {
      return this.shopId;
   }

   public void setShopId(Long shopId) {
      this.shopId = shopId;
   }

   public String getShopName() {
      return this.shopName;
   }

   public void setShopName(String shopName) {
      this.shopName = shopName;
   }

   public String getShopDesc() {
      return this.shopDesc;
   }

   public void setShopDesc(String shopDesc) {
      this.shopDesc = shopDesc;
   }

   public String getShopAddr() {
      return this.shopAddr;
   }

   public void setShopAddr(String shopAddr) {
      this.shopAddr = shopAddr;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getShopImg() {
      return this.shopImg;
   }

   public void setShopImg(String shopImg) {
      this.shopImg = shopImg;
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

   public String getAdvice() {
      return this.advice;
   }

   public void setAdvice(String advice) {
      this.advice = advice;
   }

   public Area getArea() {
      return this.area;
   }

   public void setArea(Area area) {
      this.area = area;
   }

   public PersonInfo getOwner() {
      return this.owner;
   }

   public void setOwner(PersonInfo owner) {
      this.owner = owner;
   }

   public ShopCategory getShopCategory() {
      return this.shopCategory;
   }

   public void setShopCategory(ShopCategory shopCategory) {
      this.shopCategory = shopCategory;
   }
}
