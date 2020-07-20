package com.siyu.o2o.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductCategoryDao {

   List queryProductCategoryList(long var1);

   int batchInsertProductCategory(List var1);

   int deleteProductCategory(@Param("productCategoryId") long var1, @Param("shopId") long var3);
}
