package com.siyu.o2o.dao;

import java.util.List;

public interface ProductImgDao {

   List queryProductImgList(long var1);

   int batchInsertProductImg(List var1);

   int deleteProductImgByProductId(long var1);
}
