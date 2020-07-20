package com.siyu.o2o.dao;

import com.siyu.o2o.entity.Shop;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopDao {

   List queryShopList(@Param("shopCondition") Shop var1, @Param("rowIndex") int var2, @Param("pageSize") int var3);

   List queryTop5ShopIdList();

   int queryShopCount(@Param("shopCondition") Shop var1);

   Shop queryByShopId(long var1);

   int insertShop(Shop var1);

   int updateShop(Shop var1);
}
