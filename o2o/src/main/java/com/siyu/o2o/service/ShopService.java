package com.siyu.o2o.service;

import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.dto.ShopExecution;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.exceptions.ShopOperationException;

public interface ShopService {

   ShopExecution getShopList(Shop var1, int var2, int var3);

   Shop getByShopId(long var1);

   ShopExecution modifyShop(Shop var1, ImageHolder var2) throws ShopOperationException;

   ShopExecution addShop(Shop var1, ImageHolder var2) throws ShopOperationException;
}
