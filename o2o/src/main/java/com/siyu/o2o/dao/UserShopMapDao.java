package com.siyu.o2o.dao;

import com.siyu.o2o.entity.UserShopMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserShopMapDao {

   List queryUserShopMapList(@Param("userShopCondition") UserShopMap var1, @Param("rowIndex") int var2, @Param("pageSize") int var3);

   int queryUserShopMapCount(@Param("userShopCondition") UserShopMap var1);

   UserShopMap queryUserShopMap(@Param("userId") long var1, @Param("shopId") long var3);

   int insertUserShopMap(UserShopMap var1);

   int updateUserShopMapPoint(UserShopMap var1);
}
