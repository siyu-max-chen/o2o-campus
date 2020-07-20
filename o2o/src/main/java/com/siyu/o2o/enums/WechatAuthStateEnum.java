package com.siyu.o2o.enums;


public enum WechatAuthStateEnum {

   LOGINFAIL("LOGINFAIL", 0, -1, "openId输入有误"),
   SUCCESS("SUCCESS", 1, 0, "操作成功"),
   NULL_AUTH_INFO("NULL_AUTH_INFO", 2, -1006, "注册信息为空");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final WechatAuthStateEnum[] $VALUES = new WechatAuthStateEnum[]{LOGINFAIL, SUCCESS, NULL_AUTH_INFO};


   private WechatAuthStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static WechatAuthStateEnum stateOf(int index) {
      WechatAuthStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         WechatAuthStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
