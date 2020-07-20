package com.siyu.o2o.entity;

import com.siyu.o2o.entity.Product;
import com.siyu.o2o.entity.Shop;
import java.util.Date;

public class ProductSellDaily {

   private Long productSellDailyId;
   private Date createTime;
   private Integer total;
   private Product product;
   private Shop shop;


   public Long getProductSellDailyId() {
      return this.productSellDailyId;
   }

   public void setProductSellDailyId(Long productSellDailyId) {
      this.productSellDailyId = productSellDailyId;
   }

   public Date getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public Integer getTotal() {
      return this.total;
   }

   public void setTotal(Integer total) {
      this.total = total;
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
}
