package com.siyu.o2o.enums;


public enum LocalRegisterStateEnum {

   REGISTERFAIL("REGISTERFAIL", 0, -1, "密码或帐号或邮箱输入有误"),
   SUCCESS("SUCCESS", 1, 0, "操作成功"),
   NULL_AUTH_INFO("NULL_AUTH_INFO", 2, -1006, "注册信息为空"),
   ONLY_ONE_ACCOUNT("ONLY_ONE_ACCOUNT", 3, -1007, "最多只能绑定一个本地帐号/邮箱");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final LocalRegisterStateEnum[] $VALUES = new LocalRegisterStateEnum[]{REGISTERFAIL, SUCCESS, NULL_AUTH_INFO, ONLY_ONE_ACCOUNT};


   private LocalRegisterStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static LocalRegisterStateEnum stateOf(int index) {
      LocalRegisterStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LocalRegisterStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
