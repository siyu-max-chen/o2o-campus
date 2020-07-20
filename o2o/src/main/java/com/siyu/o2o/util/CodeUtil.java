package com.siyu.o2o.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.siyu.o2o.util.HttpServletRequestUtil;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CodeUtil {

   public static boolean checkVerifyCode(HttpServletRequest request) {
      String verifyCodeExpected = (String)request.getSession().getAttribute("KAPTCHA_SESSION_KEY");
      String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
      return verifyCodeActual != null && verifyCodeActual.toLowerCase().equals(verifyCodeExpected.toLowerCase());
   }

   public static BitMatrix generateQRCodeStream(String content, HttpServletResponse resp) {
      resp.setHeader("Cache-Control", "no-store");
      resp.setHeader("Pragma", "no-cache");
      resp.setDateHeader("Expires", 0L);
      resp.setContentType("image/png");
      HashMap hints = new HashMap();
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
      hints.put(EncodeHintType.MARGIN, Integer.valueOf(0));

      try {
         BitMatrix bitMatrix = (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
         return bitMatrix;
      } catch (WriterException var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
