package com.siyu.o2o.dao;

import com.siyu.o2o.entity.HeadLine;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeadLineDao {

   List queryHeadLine(@Param("headLineCondition") HeadLine var1);

   HeadLine queryHeadLineById(long var1);

   List queryHeadLineByIds(List var1);

   int insertHeadLine(HeadLine var1);

   int updateHeadLine(HeadLine var1);

   int deleteHeadLine(long var1);

   int batchDeleteHeadLine(List var1);
}
