package com.siyu.o2o.util;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.lang3.StringUtils;

public class URLShortnerUtil {

   private static final String GOOGLE_SHORTEN_URL = "https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyCZ6gUYLIulANRhWm3wy7VbAIsrreum6EE";


   public static String shortURLGoogle(String longURL) {
      String shortURL = "";
      HttpsURLConnection con = null;

      try {
         HashMap e = new HashMap();
         e.put("longUrl", longURL);
         String requestBody = (new JSONSerializer()).serialize(e);
         con = (HttpsURLConnection)(new URL("https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyCZ6gUYLIulANRhWm3wy7VbAIsrreum6EE")).openConnection();
         con.setDoOutput(true);
         con.setDoInput(true);
         con.setRequestMethod("POST");
         con.setRequestProperty("Content-Type", "application/json");
         con.getOutputStream().write(requestBody.getBytes());
         System.out.println(con.getResponseCode());
         if(con.getResponseCode() == 200) {
            StringBuilder sb = new StringBuilder();

            try {
               BufferedReader e1 = new BufferedReader(new InputStreamReader(con.getInputStream()));
               Throwable var7 = null;

               String var10;
               try {
                  String line;
                  while((line = e1.readLine()) != null) {
                     sb.append(line);
                  }

                  Map map = (Map)(new JSONDeserializer()).deserialize(sb.toString());
                  if(map == null || !StringUtils.isNotEmpty((CharSequence)map.get("id"))) {
                     return shortURL;
                  }

                  shortURL = (String)map.get("id");
                  var10 = shortURL;
               } catch (Throwable var22) {
                  var7 = var22;
                  throw var22;
               } finally {
                  if(e1 != null) {
                     if(var7 != null) {
                        try {
                           e1.close();
                        } catch (Throwable var21) {
                           var7.addSuppressed(var21);
                        }
                     } else {
                        e1.close();
                     }
                  }

               }

               return var10;
            } catch (IOException var24) {
               var24.printStackTrace();
            }
         }
      } catch (Exception var25) {
         var25.printStackTrace();
      }

      return shortURL;
   }

   public static void main(String[] args) {
      String s = shortURLGoogle("www.chensiyu.com");
      System.out.println(s);
   }
}
