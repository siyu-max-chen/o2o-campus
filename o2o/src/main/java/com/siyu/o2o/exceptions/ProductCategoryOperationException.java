package com.siyu.o2o.exceptions;


public class ProductCategoryOperationException extends RuntimeException {

   private static final long serialVersionUID = 4403536030216412153L;


   public ProductCategoryOperationException(String msg) {
      super(msg);
   }
}
