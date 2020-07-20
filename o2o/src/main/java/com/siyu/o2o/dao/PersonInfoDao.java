package com.siyu.o2o.dao;

import com.siyu.o2o.entity.PersonInfo;

public interface PersonInfoDao {

   PersonInfo queryPersonInfoById(long var1);

   PersonInfo queryPersonInfoByEmail(String var1);

   int insertPersonInfo(PersonInfo var1);
}
