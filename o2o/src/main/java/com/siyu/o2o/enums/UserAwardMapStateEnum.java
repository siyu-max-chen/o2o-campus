package com.siyu.o2o.enums;


public enum UserAwardMapStateEnum {

   SUCCESS("SUCCESS", 0, 1, "操作成功"),
   INNER_ERROR("INNER_ERROR", 1, -1001, "操作失败"),
   NULL_USERAWARD_ID("NULL_USERAWARD_ID", 2, -1002, "UserAwardId为空"),
   NULL_USERAWARD_INFO("NULL_USERAWARD_INFO", 3, -1003, "传入了空的信息");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final UserAwardMapStateEnum[] $VALUES = new UserAwardMapStateEnum[]{SUCCESS, INNER_ERROR, NULL_USERAWARD_ID, NULL_USERAWARD_INFO};


   private UserAwardMapStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static UserAwardMapStateEnum stateOf(int index) {
      UserAwardMapStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         UserAwardMapStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
