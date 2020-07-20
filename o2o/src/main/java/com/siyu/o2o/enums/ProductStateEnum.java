package com.siyu.o2o.enums;


public enum ProductStateEnum {

   OFFLINE("OFFLINE", 0, -1, "非法商品"),
   DOWN("DOWN", 1, 0, "下架"),
   SUCCESS("SUCCESS", 2, 1, "操作成功"),
   INNER_ERROR("INNER_ERROR", 3, -1001, "操作失败"),
   EMPTY("EMPTY", 4, -1002, "商品为空");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final ProductStateEnum[] $VALUES = new ProductStateEnum[]{OFFLINE, DOWN, SUCCESS, INNER_ERROR, EMPTY};


   private ProductStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static ProductStateEnum stateOf(int index) {
      ProductStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ProductStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
