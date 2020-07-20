package com.siyu.o2o.dto;

import java.util.HashSet;

public class EchartXAxis {

   private String type = "category";
   private HashSet data;


   public HashSet getData() {
      return this.data;
   }

   public void setData(HashSet data) {
      this.data = data;
   }

   public String getType() {
      return this.type;
   }
}
