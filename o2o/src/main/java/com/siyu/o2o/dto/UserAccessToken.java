package com.siyu.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccessToken {

   @JsonProperty("access_token")
   private String accessToken;
   @JsonProperty("expires_in")
   private String expiresIn;
   @JsonProperty("refresh_token")
   private String refreshToken;
   @JsonProperty("openid")
   private String openId;
   @JsonProperty("scope")
   private String scope;


   public String getAccessToken() {
      return this.accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

   public String getExpiresIn() {
      return this.expiresIn;
   }

   public void setExpiresIn(String expiresIn) {
      this.expiresIn = expiresIn;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
   }

   public String getOpenId() {
      return this.openId;
   }

   public void setOpenId(String openId) {
      this.openId = openId;
   }

   public String getScope() {
      return this.scope;
   }

   public void setScope(String scope) {
      this.scope = scope;
   }

   public String toString() {
      return "accessToken:" + this.getAccessToken() + ",openId:" + this.getOpenId();
   }
}
