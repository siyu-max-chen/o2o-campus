package com.siyu.o2o.entity;

import com.siyu.o2o.entity.ProductCategory;
import com.siyu.o2o.entity.Shop;
import java.util.Date;
import java.util.List;

public class Product {

   private Long productId;
   private String productName;
   private String productDesc;
   private String imgAddr;
   private String normalPrice;
   private String promotionPrice;
   private Integer priority;
   private Integer point;
   private Date createTime;
   private Date lastEditTime;
   private Integer enableStatus;
   private List productImgList;
   private ProductCategory productCategory;
   private Shop shop;


   public Long getProductId() {
      return this.productId;
   }

   public void setProductId(Long productId) {
      this.productId = productId;
   }

   public String getProductName() {
      return this.productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   public String getProductDesc() {
      return this.productDesc;
   }

   public void setProductDesc(String productDesc) {
      this.productDesc = productDesc;
   }

   public String getImgAddr() {
      return this.imgAddr;
   }

   public void setImgAddr(String imgAddr) {
      this.imgAddr = imgAddr;
   }

   public String getNormalPrice() {
      return this.normalPrice;
   }

   public void setNormalPrice(String normalPrice) {
      this.normalPrice = normalPrice;
   }

   public String getPromotionPrice() {
      return this.promotionPrice;
   }

   public void setPromotionPrice(String promotionPrice) {
      this.promotionPrice = promotionPrice;
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

   public Integer getPoint() {
      return this.point;
   }

   public void setPoint(Integer point) {
      this.point = point;
   }

   public List getProductImgList() {
      return this.productImgList;
   }

   public void setProductImgList(List productImgList) {
      this.productImgList = productImgList;
   }

   public ProductCategory getProductCategory() {
      return this.productCategory;
   }

   public void setProductCategory(ProductCategory productCategory) {
      this.productCategory = productCategory;
   }

   public Shop getShop() {
      return this.shop;
   }

   public void setShop(Shop shop) {
      this.shop = shop;
   }

   public String toString() {
      return "Product [productId=" + this.productId + ", productName=" + this.productName + ", productDesc=" + this.productDesc + ", imgAddr=" + this.imgAddr + ", normalPrice=" + this.normalPrice + ", promotionPrice=" + this.promotionPrice + ", priority=" + this.priority + ", point=" + this.point + ", createTime=" + this.createTime + ", lastEditTime=" + this.lastEditTime + ", enableStatus=" + this.enableStatus + ", productImgList=" + this.productImgList + ", productCategory=" + this.productCategory + ", shop=" + this.shop + "]";
   }
}
