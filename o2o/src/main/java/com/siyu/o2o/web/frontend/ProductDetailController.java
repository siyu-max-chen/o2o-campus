package com.siyu.o2o.web.frontend;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.service.ProductService;
import com.siyu.o2o.util.CodeUtil;
import com.siyu.o2o.util.HttpServletRequestUtil;
import com.siyu.o2o.util.ShortNetAddressUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/frontend"})
public class ProductDetailController {

   @Autowired
   private ProductService productService;
   private static String urlPrefix;
   private static String urlMiddle;
   private static String urlSuffix;
   private static String productmapUrl;


   @RequestMapping(
      value = {"/listproductdetailpageinfo"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listProductDetailPageInfo(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      long productId = HttpServletRequestUtil.getLong(request, "productId");
      Product product = null;
      if(productId != -1L) {
         product = this.productService.getProductById(productId);
         PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
         if(user == null) {
            modelMap.put("needQRCode", Boolean.valueOf(false));
         } else {
            modelMap.put("needQRCode", Boolean.valueOf(true));
         }

         modelMap.put("product", product);
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty productId");
      }

      return modelMap;
   }

   @Value("${wechat.prefix}")
   public void setUrlPrefix(String urlPrefix) {
      urlPrefix = urlPrefix;
   }

   @Value("${wechat.middle}")
   public void setUrlMiddle(String urlMiddle) {
      urlMiddle = urlMiddle;
   }

   @Value("${wechat.suffix}")
   public void setUrlSuffix(String urlSuffix) {
      urlSuffix = urlSuffix;
   }

   @Value("${wechat.productmap.url}")
   public void setProductmapUrl(String productmapUrl) {
      productmapUrl = productmapUrl;
   }

   @RequestMapping(
      value = {"/generateqrcode4product"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private void generateQRCode4Product(HttpServletRequest request, HttpServletResponse response) {
      long productId = HttpServletRequestUtil.getLong(request, "productId");
      PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
      if(productId != -1L && user != null && user.getUserId() != null) {
         long timpStamp = System.currentTimeMillis();
         String content = "{aaaproductIdaaa:" + productId + ",aaacustomerIdaaa:" + user.getUserId() + ",aaacreateTimeaaa:" + timpStamp + "}";

         try {
            String e = urlPrefix + productmapUrl + urlMiddle + URLEncoder.encode(content, "UTF-8") + urlSuffix;
            String shortUrl = ShortNetAddressUtil.getShortURL(e);
            BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
            MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
         } catch (IOException var12) {
            var12.printStackTrace();
         }
      }

   }
}
