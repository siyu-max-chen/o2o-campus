package com.siyu.o2o.enums;


public enum UserShopMapStateEnum {

   SUCCESS("SUCCESS", 0, 1, "操作成功"),
   INNER_ERROR("INNER_ERROR", 1, -1001, "操作失败"),
   NULL_USERSHOP_ID("NULL_USERSHOP_ID", 2, -1002, "UserShopId为空"),
   NULL_USERSHOP_INFO("NULL_USERSHOP_INFO", 3, -1003, "传入了空的信息");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final UserShopMapStateEnum[] $VALUES = new UserShopMapStateEnum[]{SUCCESS, INNER_ERROR, NULL_USERSHOP_ID, NULL_USERSHOP_INFO};


   private UserShopMapStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static UserShopMapStateEnum stateOf(int index) {
      UserShopMapStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         UserShopMapStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
