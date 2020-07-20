package com.siyu.o2o;

import com.siyu.o2o.service.PersonInfoService;
import com.siyu.o2o.service.ProductService;
import com.siyu.o2o.service.UserProductMapService;
import com.siyu.o2o.util.SellAnalogUtil;
import com.siyu.o2o.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class O2oApplication {

   public static void main(String[] args) {
      SpringApplication.run(O2oApplication.class, args);
      ApplicationContext context = SpringUtil.getApplicationContext();
      SellAnalogUtil.productService = (ProductService)context.getBean(ProductService.class);
      SellAnalogUtil.personInfoService = (PersonInfoService)context.getBean(PersonInfoService.class);
      SellAnalogUtil.userProductMapService = (UserProductMapService)context.getBean(UserProductMapService.class);
   }
}
