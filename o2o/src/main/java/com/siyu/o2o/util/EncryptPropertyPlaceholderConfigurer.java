package com.siyu.o2o.util;

import com.siyu.o2o.util.DESUtil;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

   private String[] encryptPropNames = new String[]{"jdbc.username", "jdbc.password"};


   protected String convertProperty(String propertyName, String propertyValue) {
      if(this.isEncryptProp(propertyName)) {
         String decryptValue = DESUtil.getDecryptString(propertyValue);
         return decryptValue;
      } else {
         return propertyValue;
      }
   }

   private boolean isEncryptProp(String propertyName) {
      String[] var2 = this.encryptPropNames;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String encryptpropertyName = var2[var4];
         if(encryptpropertyName.equals(propertyName)) {
            return true;
         }
      }

      return false;
   }
}
