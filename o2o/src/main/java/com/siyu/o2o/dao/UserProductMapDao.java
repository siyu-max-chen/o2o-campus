package com.siyu.o2o.dao;

import com.siyu.o2o.entity.UserProductMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserProductMapDao {

   List queryUserProductMapList(@Param("userProductCondition") UserProductMap var1, @Param("rowIndex") int var2, @Param("pageSize") int var3);

   int queryUserProductMapCount(@Param("userProductCondition") UserProductMap var1);

   int insertUserProductMap(UserProductMap var1);
}
