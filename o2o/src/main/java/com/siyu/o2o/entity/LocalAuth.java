package com.siyu.o2o.entity;

import com.siyu.o2o.entity.PersonInfo;
import java.util.Date;

public class LocalAuth {

   private Long localAuthId;
   private String username;
   private String email;
   private String password;
   private Date createTime;
   private Date lastEditTime;
   private PersonInfo personInfo;


   public Long getLocalAuthId() {
      return this.localAuthId;
   }

   public void setLocalAuthId(Long localAuthId) {
      this.localAuthId = localAuthId;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
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

   public PersonInfo getPersonInfo() {
      return this.personInfo;
   }

   public void setPersonInfo(PersonInfo personInfo) {
      this.personInfo = personInfo;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }
}
