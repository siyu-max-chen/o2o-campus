package com.siyu.o2o.enums;


public enum AwardStateEnum {

   OFFLINE("OFFLINE", 0, -1, "非法奖品"),
   SUCCESS("SUCCESS", 1, 0, "操作成功"),
   INNER_ERROR("INNER_ERROR", 2, -1001, "操作失败"),
   EMPTY("EMPTY", 3, -1002, "奖品信息为空");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final AwardStateEnum[] $VALUES = new AwardStateEnum[]{OFFLINE, SUCCESS, INNER_ERROR, EMPTY};


   private AwardStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static AwardStateEnum stateOf(int index) {
      AwardStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         AwardStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
