package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.ProductSellDailyDao;
import com.siyu.o2o.entity.ProductSellDaily;
import com.siyu.o2o.service.ProductSellDailyService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {

   private static final Logger log = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);
   @Autowired
   private ProductSellDailyDao productSellDailyDao;


   public List listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime) {
      return this.productSellDailyDao.queryProductSellDailyList(productSellDailyCondition, beginTime, endTime);
   }

   public void dailyCalculate() {
      log.info("Quartz Running!");
      this.productSellDailyDao.insertProductSellDaily();
      this.productSellDailyDao.insertDefaultProductSellDaily();
   }

}
