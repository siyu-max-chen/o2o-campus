package com.siyu.o2o.enums;


public enum ProductCategoryStateEnum {

   SUCCESS("SUCCESS", 0, 1, "创建成功"),
   INNER_ERROR("INNER_ERROR", 1, -1001, "操作失败"),
   EMPTY_LIST("EMPTY_LIST", 2, -1002, "添加数少于1");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final ProductCategoryStateEnum[] $VALUES = new ProductCategoryStateEnum[]{SUCCESS, INNER_ERROR, EMPTY_LIST};


   private ProductCategoryStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static ProductCategoryStateEnum stateOf(int index) {
      ProductCategoryStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ProductCategoryStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
