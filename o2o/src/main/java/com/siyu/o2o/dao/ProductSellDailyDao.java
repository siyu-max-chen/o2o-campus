package com.siyu.o2o.dao;

import com.siyu.o2o.entity.ProductSellDaily;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSellDailyDao {

   List queryProductSellDailyList(@Param("productSellDailyCondition") ProductSellDaily var1, @Param("beginTime") Date var2, @Param("endTime") Date var3);

   int insertProductSellDaily();

   int insertDefaultProductSellDaily();
}
