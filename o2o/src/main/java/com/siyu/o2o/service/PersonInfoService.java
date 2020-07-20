package com.siyu.o2o.service;

import com.siyu.o2o.entity.PersonInfo;

public interface PersonInfoService {

   PersonInfo getPersonInfoById(Long var1);

   PersonInfo getPersonInfoByEmail(String var1);
}
