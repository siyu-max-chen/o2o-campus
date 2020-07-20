package com.siyu.o2o.entity;

import java.util.Date;

public class Area {

   private Integer areaId;
   private String areaName;
   private Integer priority;
   private Date createTime;
   private Date lastEditTime;


   public Integer getAreaId() {
      return this.areaId;
   }

   public void setAreaId(Integer areaId) {
      this.areaId = areaId;
   }

   public String getAreaName() {
      return this.areaName;
   }

   public void setAreaName(String areaName) {
      this.areaName = areaName;
   }

   public Integer getPriority() {
      return this.priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
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
