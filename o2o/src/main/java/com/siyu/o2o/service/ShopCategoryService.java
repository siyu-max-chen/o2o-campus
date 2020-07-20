package com.siyu.o2o.service;

import com.siyu.o2o.entity.ShopCategory;
import java.util.List;

public interface ShopCategoryService {

   String SCLISTKEY = "shopcategorylist";


   List getShopCategoryList(ShopCategory var1);
}
