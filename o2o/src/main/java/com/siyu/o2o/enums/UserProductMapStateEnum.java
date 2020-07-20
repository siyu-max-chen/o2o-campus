package com.siyu.o2o.enums;


public enum UserProductMapStateEnum {

   SUCCESS("SUCCESS", 0, 1, "操作成功"),
   INNER_ERROR("INNER_ERROR", 1, -1001, "操作失败"),
   NULL_USERPRODUCT_ID("NULL_USERPRODUCT_ID", 2, -1002, "UserProductId为空"),
   NULL_USERPRODUCT_INFO("NULL_USERPRODUCT_INFO", 3, -1003, "传入了空的信息");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final UserProductMapStateEnum[] $VALUES = new UserProductMapStateEnum[]{SUCCESS, INNER_ERROR, NULL_USERPRODUCT_ID, NULL_USERPRODUCT_INFO};


   private UserProductMapStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static UserProductMapStateEnum stateOf(int index) {
      UserProductMapStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         UserProductMapStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
