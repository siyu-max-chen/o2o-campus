package com.siyu.o2o.dto;


public class Result {

   private boolean success;
   private Object data;
   private String errorMsg;
   private int errorCode;


   public Result() {}

   public Result(boolean success, Object data) {
      this.success = success;
      this.data = data;
   }

   public Result(boolean success, int errorCode, String errorMsg) {
      this.success = success;
      this.errorMsg = errorMsg;
      this.errorCode = errorCode;
   }

   public boolean isSuccess() {
      return this.success;
   }

   public void setSuccess(boolean success) {
      this.success = success;
   }

   public Object getData() {
      return this.data;
   }

   public void setData(Object data) {
      this.data = data;
   }

   public String getErrorMsg() {
      return this.errorMsg;
   }

   public void setErrorMsg(String errorMsg) {
      this.errorMsg = errorMsg;
   }

   public int getErrorCode() {
      return this.errorCode;
   }

   public void setErrorCode(int errorCode) {
      this.errorCode = errorCode;
   }
}
