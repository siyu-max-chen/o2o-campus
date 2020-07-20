package com.siyu.o2o.web.superadmin;

import com.siyu.o2o.service.AreaService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/superadmin"})
public class AreaController {

   Logger logger = LoggerFactory.getLogger(AreaController.class);
   @Autowired
   private AreaService areaService;


   @RequestMapping(
      value = {"/listarea"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listArea() {
      this.logger.info("=========start===========");
      long startTime = System.currentTimeMillis();
      HashMap modelMap = new HashMap();
      new ArrayList();

      try {
         List list = this.areaService.getAreaList();
         modelMap.put("rows", list);
         modelMap.put("total", Integer.valueOf(list.size()));
      } catch (Exception var7) {
         var7.printStackTrace();
         modelMap.put("sucess", Boolean.valueOf(false));
         modelMap.put("errMsg", var7.toString());
      }

      this.logger.error("test error!");
      long endTime = System.currentTimeMillis();
      this.logger.debug("costTime:[{}ms]", Long.valueOf(endTime - startTime));
      this.logger.info("=========end===========");
      return modelMap;
   }
}
