package com.siyu.o2o.service;

import com.siyu.o2o.dto.UserShopMapExecution;
import com.siyu.o2o.entity.UserShopMap;

public interface UserShopMapService {

   UserShopMapExecution listUserShopMap(UserShopMap var1, int var2, int var3);

   UserShopMap getUserShopMap(long var1, long var3);
}
