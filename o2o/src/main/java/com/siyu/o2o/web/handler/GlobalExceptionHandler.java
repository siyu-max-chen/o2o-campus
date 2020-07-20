package com.siyu.o2o.web.handler;

import com.siyu.o2o.exceptions.ProductOperationException;
import com.siyu.o2o.exceptions.ShopOperationException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

   private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);


   @ExceptionHandler({Exception.class})
   @ResponseBody
   public Map handle(Exception e) {
      HashMap modelMap = new HashMap();
      modelMap.put("success", Boolean.valueOf(false));
      if(e instanceof ShopOperationException) {
         modelMap.put("errMsg", e.getMessage());
      } else if(e instanceof ProductOperationException) {
         modelMap.put("errMsg", e.getMessage());
      } else {
         LOG.error("系统出现异常", e.getMessage());
         modelMap.put("errMsg", "未知错误，请联系工作人员进行解决");
      }

      return modelMap;
   }

}
