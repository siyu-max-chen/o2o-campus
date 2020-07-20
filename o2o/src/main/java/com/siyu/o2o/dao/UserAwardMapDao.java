package com.siyu.o2o.dao;

import com.siyu.o2o.entity.UserAwardMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAwardMapDao {

   List queryUserAwardMapList(@Param("userAwardCondition") UserAwardMap var1, @Param("rowIndex") int var2, @Param("pageSize") int var3);

   List queryReceivedUserAwardMapList(@Param("userAwardCondition") UserAwardMap var1, @Param("rowIndex") int var2, @Param("pageSize") int var3);

   int queryUserAwardMapCount(@Param("userAwardCondition") UserAwardMap var1);

   UserAwardMap queryUserAwardMapById(long var1);

   int insertUserAwardMap(UserAwardMap var1);

   int updateUserAwardMap(UserAwardMap var1);
}
