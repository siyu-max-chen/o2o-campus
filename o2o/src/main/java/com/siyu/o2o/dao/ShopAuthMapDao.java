package com.siyu.o2o.dao;

import com.siyu.o2o.entity.ShopAuthMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopAuthMapDao {

   List queryShopAuthMapListByShopId(@Param("shopId") long var1, @Param("rowIndex") int var3, @Param("pageSize") int var4);

   int queryShopAuthCountByShopId(@Param("shopId") long var1);

   int insertShopAuthMap(ShopAuthMap var1);

   int updateShopAuthMap(ShopAuthMap var1);

   int deleteShopAuthMap(long var1);

   ShopAuthMap queryShopAuthMapById(Long var1);
}
