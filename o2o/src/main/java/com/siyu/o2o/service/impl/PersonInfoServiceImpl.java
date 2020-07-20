package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.PersonInfoDao;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

   @Autowired
   private PersonInfoDao personInfoDao;


   public PersonInfo getPersonInfoById(Long userId) {
      return this.personInfoDao.queryPersonInfoById(userId.longValue());
   }

   public PersonInfo getPersonInfoByEmail(String email) {
      return this.personInfoDao.queryPersonInfoByEmail(email);
   }
}
