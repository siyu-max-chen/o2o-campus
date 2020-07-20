package com.siyu.o2o.dao;

import com.siyu.o2o.entity.UserShopLikeMap;
import org.apache.ibatis.annotations.Param;

public interface UserShopLikeDao {

   UserShopLikeMap queryByUserIdShopId(@Param("userId") long var1, @Param("shopId") long var3);

   int insertUserShopLike(UserShopLikeMap var1);

   int deleteUserShopLike(@Param("userId") long var1, @Param("shopId") long var3);
}
