package com.siyu.o2o.dao;

import com.siyu.o2o.entity.Award;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AwardDao {

   List queryAwardList(@Param("awardCondition") Award var1, @Param("rowIndex") int var2, @Param("pageSize") int var3);

   int queryAwardCount(@Param("awardCondition") Award var1);

   Award queryAwardByAwardId(long var1);

   int insertAward(Award var1);

   int updateAward(Award var1);

   int deleteAward(@Param("awardId") long var1, @Param("shopId") long var3);
}
