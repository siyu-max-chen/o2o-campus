package com.siyu.o2o.service;

import com.siyu.o2o.dto.UserAwardMapExecution;
import com.siyu.o2o.entity.UserAwardMap;
import com.siyu.o2o.exceptions.UserAwardMapOperationException;

public interface UserAwardMapService {

   UserAwardMapExecution listUserAwardMap(UserAwardMap var1, Integer var2, Integer var3);

   UserAwardMapExecution listReceivedUserAwardMap(UserAwardMap var1, Integer var2, Integer var3);

   UserAwardMap getUserAwardMapById(long var1);

   UserAwardMapExecution addUserAwardMap(UserAwardMap var1) throws UserAwardMapOperationException;

   UserAwardMapExecution modifyUserAwardMap(UserAwardMap var1) throws UserAwardMapOperationException;
}
