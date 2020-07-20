package com.siyu.o2o.dto;

import com.siyu.o2o.entity.Product;
import com.siyu.o2o.enums.ProductStateEnum;
import java.util.List;

public class ProductExecution {

   private int state;
   private String stateInfo;
   private int count;
   private Product product;
   private List productList;


   public ProductExecution() {}

   public ProductExecution(ProductStateEnum stateEnum) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
   }

   public ProductExecution(ProductStateEnum stateEnum, Product product) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.product = product;
   }

   public ProductExecution(ProductStateEnum stateEnum, List productList) {
      this.state = stateEnum.getState();
      this.stateInfo = stateEnum.getStateInfo();
      this.productList = productList;
   }

   public int getState() {
      return this.state;
   }

   public void setState(int state) {
      this.state = state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public void setStateInfo(String stateInfo) {
      this.stateInfo = stateInfo;
   }

   public int getCount() {
      return this.count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public List getProductList() {
      return this.productList;
   }

   public void setProductList(List productList) {
      this.productList = productList;
   }
}
