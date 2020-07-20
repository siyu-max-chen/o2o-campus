package com.siyu.o2o.dto;

import java.util.List;

public class EchartSeries {

   private String name;
   private String type = "bar";
   private List data;


   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List getData() {
      return this.data;
   }

   public void setData(List data) {
      this.data = data;
   }

   public String getType() {
      return this.type;
   }
}
