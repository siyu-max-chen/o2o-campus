package com.siyu.o2o.service;

import com.siyu.o2o.dto.UserProductMapExecution;
import com.siyu.o2o.entity.UserProductMap;
import com.siyu.o2o.exceptions.UserProductMapOperationException;

public interface UserProductMapService {

   UserProductMapExecution listUserProductMap(UserProductMap var1, Integer var2, Integer var3);

   UserProductMapExecution addUserProductMap(UserProductMap var1) throws UserProductMapOperationException;
}
