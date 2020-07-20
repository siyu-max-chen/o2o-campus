package com.siyu.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {

   public static int getInt(HttpServletRequest request, String key) {
      try {
         return Integer.decode(request.getParameter(key)).intValue();
      } catch (Exception var3) {
         return -1;
      }
   }

   public static long getLong(HttpServletRequest request, String key) {
      try {
         return Long.valueOf(request.getParameter(key)).longValue();
      } catch (Exception var3) {
         return -1L;
      }
   }

   public static Double getDouble(HttpServletRequest request, String key) {
      try {
         return Double.valueOf(request.getParameter(key));
      } catch (Exception var3) {
         return Double.valueOf(-1.0D);
      }
   }

   public static boolean getBoolean(HttpServletRequest request, String key) {
      try {
         return Boolean.valueOf(request.getParameter(key)).booleanValue();
      } catch (Exception var3) {
         return false;
      }
   }

   public static String getString(HttpServletRequest request, String key) {
      try {
         String e = request.getParameter(key);
         if(e != null) {
            e = e.trim();
         }

         if("".equals(e)) {
            e = null;
         }

         return e;
      } catch (Exception var3) {
         return null;
      }
   }
}
