package com.siyu.o2o.service;

import com.siyu.o2o.dto.ShopAuthMapExecution;
import com.siyu.o2o.entity.ShopAuthMap;
import com.siyu.o2o.exceptions.ShopAuthMapOperationException;

public interface ShopAuthMapService {

   ShopAuthMapExecution listShopAuthMapByShopId(Long var1, Integer var2, Integer var3);

   ShopAuthMap getShopAuthMapById(Long var1);

   ShopAuthMapExecution addShopAuthMap(ShopAuthMap var1) throws ShopAuthMapOperationException;

   ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap var1) throws ShopAuthMapOperationException;
}
