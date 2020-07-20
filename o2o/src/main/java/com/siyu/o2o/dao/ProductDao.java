package com.siyu.o2o.dao;

import com.siyu.o2o.entity.Product;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductDao {

   List queryProductList(@Param("productCondition") Product var1, @Param("rowIndex") int var2, @Param("pageSize") int var3);

   int queryProductCount(@Param("productCondition") Product var1);

   Product queryProductById(long var1);

   int insertProduct(Product var1);

   int updateProduct(Product var1);

   int updateProductCategoryToNull(long var1);

   int deleteProduct(@Param("productId") long var1, @Param("shopId") long var3);
}
