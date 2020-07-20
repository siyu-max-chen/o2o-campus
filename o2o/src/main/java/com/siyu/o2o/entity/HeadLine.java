package com.siyu.o2o.entity;

import java.util.Date;

public class HeadLine {

   private Long lineId;
   private String lineName;
   private String lineLink;
   private String lineImg;
   private Integer priority;
   private Integer enableStatus;
   private Date createTime;
   private Date lastEditTime;


   public Long getLineId() {
      return this.lineId;
   }

   public void setLineId(Long lineId) {
      this.lineId = lineId;
   }

   public String getLineName() {
      return this.lineName;
   }

   public void setLineName(String lineName) {
      this.lineName = lineName;
   }

   public String getLineLink() {
      return this.lineLink;
   }

   public void setLineLink(String lineLink) {
      this.lineLink = lineLink;
   }

   public String getLineImg() {
      return this.lineImg;
   }

   public void setLineImg(String lineImg) {
      this.lineImg = lineImg;
   }

   public Integer getPriority() {
      return this.priority;
   }

   public void setPriority(Integer priority) {
      this.priority = priority;
   }

   public Integer getEnableStatus() {
      return this.enableStatus;
   }

   public void setEnableStatus(Integer enableStatus) {
      this.enableStatus = enableStatus;
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
