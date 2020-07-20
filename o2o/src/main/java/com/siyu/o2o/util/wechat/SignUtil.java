package com.siyu.o2o.util.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {

   private static String token = "o2o";


   public static boolean checkSignature(String signature, String timestamp, String nonce) {
      String[] arr = new String[]{token, timestamp, nonce};
      Arrays.sort(arr);
      StringBuilder content = new StringBuilder();

      for(int md = 0; md < arr.length; ++md) {
         content.append(arr[md]);
      }

      MessageDigest var9 = null;
      String tmpStr = null;

      try {
         var9 = MessageDigest.getInstance("SHA-1");
         byte[] e = var9.digest(content.toString().getBytes());
         tmpStr = byteToStr(e);
      } catch (NoSuchAlgorithmException var8) {
         var8.printStackTrace();
      }

      content = null;
      return tmpStr != null?tmpStr.equals(signature.toUpperCase()):false;
   }

   private static String byteToStr(byte[] byteArray) {
      String strDigest = "";

      for(int i = 0; i < byteArray.length; ++i) {
         strDigest = strDigest + byteToHexStr(byteArray[i]);
      }

      return strDigest;
   }

   private static String byteToHexStr(byte mByte) {
      char[] Digit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
      char[] tempArr = new char[]{Digit[mByte >>> 4 & 15], Digit[mByte & 15]};
      String s = new String(tempArr);
      return s;
   }

}
