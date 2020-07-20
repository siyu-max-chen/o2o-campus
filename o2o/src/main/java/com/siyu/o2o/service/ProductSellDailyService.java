package com.siyu.o2o.service;

import com.siyu.o2o.entity.ProductSellDaily;
import java.util.Date;
import java.util.List;

public interface ProductSellDailyService {

   void dailyCalculate();

   List listProductSellDaily(ProductSellDaily var1, Date var2, Date var3);
}
