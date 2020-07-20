package com.siyu.o2o.dao;

import com.siyu.o2o.entity.ShopCategory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopCategoryDao {

   List queryShopCategory(@Param("shopCategoryCondition") ShopCategory var1);
}
