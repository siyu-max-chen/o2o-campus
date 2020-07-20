package com.siyu.o2o.entity;

import java.util.Date;

public class PersonInfo {

   private Long userId;
   private String name;
   private String profileImg;
   private String email;
   private String gender;
   private Integer enableStatus;
   private Integer userType;
   private Date createTime;
   private Date lastEditTime;


   public Long getUserId() {
      return this.userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getProfileImg() {
      return this.profileImg;
   }

   public void setProfileImg(String profileImg) {
      this.profileImg = profileImg;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getGender() {
      return this.gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public Integer getEnableStatus() {
      return this.enableStatus;
   }

   public void setEnableStatus(Integer enableStatus) {
      this.enableStatus = enableStatus;
   }

   public Integer getUserType() {
      return this.userType;
   }

   public void setUserType(Integer userType) {
      this.userType = userType;
   }

   public Date getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public Date getLastEditTime() {
      return this.lastEditTime;
   }

   public void setLastEditTime(Date lastEditTime) {
      this.lastEditTime = lastEditTime;
   }
}
