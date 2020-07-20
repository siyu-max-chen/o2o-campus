package com.siyu.o2o.util;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class DESUtil {

   private static Key key;
   private static String KEY_STR = "myKey";
   private static String CHARSETNAME = "UTF-8";
   private static String ALGORITHM = "DES";


   public static String getEncryptString(String str) {
      try {
         byte[] e = str.getBytes(CHARSETNAME);
         Cipher cipher = Cipher.getInstance(ALGORITHM);
         cipher.init(1, key);
         byte[] doFinal = cipher.doFinal(e);
         return Base64.getEncoder().encodeToString(doFinal);
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public static String getDecryptString(String str) {
      try {
         byte[] e = Base64.getDecoder().decode(str);
         Cipher cipher = Cipher.getInstance(ALGORITHM);
         cipher.init(2, key);
         byte[] doFinal = cipher.doFinal(e);
         return new String(doFinal, CHARSETNAME);
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public static void main(String[] args) {
      String s1 = getEncryptString("work");
      String s2 = getEncryptString("Yuhao360!");
      System.out.println(s1);
      System.out.println(s2);
      System.out.println(getDecryptString(s1));
      System.out.println(getDecryptString(s2));
   }

   static {
      try {
         KeyGenerator e = KeyGenerator.getInstance(ALGORITHM);
         SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
         secureRandom.setSeed(KEY_STR.getBytes());
         e.init(secureRandom);
         key = e.generateKey();
         e = null;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }
}
