package com.siyu.o2o.enums;


public enum LocalAuthStateEnum {

   LOGINFAIL("LOGINFAIL", 0, -1, "Invalid user name or password"),
   SUCCESS("SUCCESS", 1, 0, "Success"),
   NULL_AUTH_INFO("NULL_AUTH_INFO", 2, -1006, "Information is empty"),
   ONLY_ONE_ACCOUNT("ONLY_ONE_ACCOUNT", 3, -1007, "You can only bind one account"),
   USER_NAME_USED("USER_NAME_USED", 4, -1008, "User name already been used!"),
   EMAIL_USED("EMAIL_USED", 5, -1009, "Email already been used!");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final LocalAuthStateEnum[] $VALUES = new LocalAuthStateEnum[]{LOGINFAIL, SUCCESS, NULL_AUTH_INFO, ONLY_ONE_ACCOUNT, USER_NAME_USED, EMAIL_USED};


   private LocalAuthStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

   public static LocalAuthStateEnum stateOf(int index) {
      LocalAuthStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LocalAuthStateEnum state = var1[var3];
         if(state.getState() == index) {
            return state;
         }
      }

      return null;
   }

}
