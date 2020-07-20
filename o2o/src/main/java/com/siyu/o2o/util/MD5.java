package com.siyu.o2o.util;

import java.security.MessageDigest;

public class MD5 {

   public static final String getMd5(String s) {
      char[] hexDigits = new char[]{'5', '0', '5', '6', '2', '9', '6', '2', '5', 'q', 'b', 'l', 'e', 's', 's', 'y'};

      try {
         byte[] strTemp = s.getBytes();
         MessageDigest mdTemp = MessageDigest.getInstance("MD5");
         mdTemp.update(strTemp);
         byte[] md = mdTemp.digest();
         int j = md.length;
         char[] e = new char[j * 2];
         int k = 0;

         for(int i = 0; i < j; ++i) {
            byte byte0 = md[i];
            e[k++] = hexDigits[byte0 >>> 4 & 15];
            e[k++] = hexDigits[byte0 & 15];
         }

         return new String(e);
      } catch (Exception var10) {
         return null;
      }
   }

   public static void main(String[] args) {
      System.out.println(getMd5("123"));
   }
}
