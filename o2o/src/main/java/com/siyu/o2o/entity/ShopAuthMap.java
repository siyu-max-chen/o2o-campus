package com.siyu.o2o.entity;

import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import java.util.Date;

public class ShopAuthMap {

   private Long shopAuthId;
   private String title;
   private Integer titleFlag;
   private Integer enableStatus;
   private Date createTime;
   private Date lastEditTime;
   private PersonInfo employee;
   private Shop shop;


   public Long getShopAuthId() {
      return this.shopAuthId;
   }

   public void setShopAuthId(Long shopAuthId) {
      this.shopAuthId = shopAuthId;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public Integer getTitleFlag() {
      return this.titleFlag;
   }

   public void setTitleFlag(Integer titleFlag) {
      this.titleFlag = titleFlag;
   }

   public Integer getEnableStatus() {
      return this.enableStatus;
   }

   public void setEnableStatus(Integer enableStatus) {
      this.enableStatus = enableStatus;
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

   public PersonInfo getEmployee() {
      return this.employee;
   }

   public void setEmployee(PersonInfo employee) {
      this.employee = employee;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }
}
