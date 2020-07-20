package com.siyu.o2o.enums;


public enum ShopStateEnum {

   CHECK("CHECK", 0, 0, "审核中"),
   OFFLINE("OFFLINE", 1, -1, "非法店铺"),
   SUCCESS("SUCCESS", 2, 1, "操作成功"),
   PASS("PASS", 3, 2, "通过认证"),
   INNER_ERROR("INNER_ERROR", 4, -1001, "内部系统错误"),
   NULL_SHOPID("NULL_SHOPID", 5, -1002, "ShopId为空"),
   NULL_SHOP("NULL_SHOP", 6, -1003, "shop信息为空");
   private int state;
   private String stateInfo;
   // $FF: synthetic field
   private static final ShopStateEnum[] $VALUES = new ShopStateEnum[]{CHECK, OFFLINE, SUCCESS, PASS, INNER_ERROR, NULL_SHOPID, NULL_SHOP};


   private ShopStateEnum(String var1, int var2, int state, String stateInfo) {
      this.state = state;
      this.stateInfo = stateInfo;
   }

   public static ShopStateEnum stateOf(int state) {
      ShopStateEnum[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ShopStateEnum stateEnum = var1[var3];
         if(stateEnum.getState() == state) {
            return stateEnum;
         }
      }

      return null;
   }

   public int getState() {
      return this.state;
   }

   public String getStateInfo() {
      return this.stateInfo;
   }

}
