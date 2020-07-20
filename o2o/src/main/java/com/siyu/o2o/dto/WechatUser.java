package com.siyu.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WechatUser {

   private static final long serialVersionUID = -4684067645282292327L;
   @JsonProperty("openid")
   private String openId;
   @JsonProperty("nickname")
   private String nickName;
   @JsonProperty("sex")
   private int sex;
   @JsonProperty("province")
   private String province;
   @JsonProperty("city")
   private String city;
   @JsonProperty("country")
   private String country;
   @JsonProperty("headimgurl")
   private String headimgurl;
   @JsonProperty("language")
   private String language;
   @JsonProperty("privilege")
   private String[] privilege;


   public String getOpenId() {
      return this.openId;
   }

   public void setOpenId(String openId) {
      this.openId = openId;
   }

   public String getNickName() {
      return this.nickName;
   }

   public void setNickName(String nickName) {
      this.nickName = nickName;
   }

   public int getSex() {
      return this.sex;
   }

   public void setSex(int sex) {
      this.sex = sex;
   }

   public String getProvince() {
      return this.province;
   }

   public void setProvince(String province) {
      this.province = province;
   }

   public String getCity() {
      return this.city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCountry() {
      return this.country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getHeadimgurl() {
      return this.headimgurl;
   }

   public void setHeadimgurl(String headimgurl) {
      this.headimgurl = headimgurl;
   }

   public String getLanguage() {
      return this.language;
   }

   public void setLanguage(String language) {
      this.language = language;
   }

   public String[] getPrivilege() {
      return this.privilege;
   }

   public void setPrivilege(String[] privilege) {
      this.privilege = privilege;
   }

   public String toString() {
      return "openId:" + this.getOpenId() + ",nikename:" + this.getNickName();
   }
}
