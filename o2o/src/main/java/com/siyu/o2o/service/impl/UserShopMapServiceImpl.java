package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.UserShopMapDao;
import com.siyu.o2o.dto.UserShopMapExecution;
import com.siyu.o2o.entity.UserShopMap;
import com.siyu.o2o.service.UserShopMapService;
import com.siyu.o2o.util.PageCalculator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserShopMapServiceImpl implements UserShopMapService {

   @Autowired
   private UserShopMapDao userShopMapDao;


   public UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int pageIndex, int pageSize) {
      if(userShopMapCondition != null && pageIndex != -1 && pageSize != -1) {
         int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
         List userShopMapList = this.userShopMapDao.queryUserShopMapList(userShopMapCondition, beginIndex, pageSize);
         int count = this.userShopMapDao.queryUserShopMapCount(userShopMapCondition);
         UserShopMapExecution ue = new UserShopMapExecution();
         ue.setUserShopMapList(userShopMapList);
         ue.setCount(Integer.valueOf(count));
         return ue;
      } else {
         return null;
      }
   }

   public UserShopMap getUserShopMap(long userId, long shopId) {
      return this.userShopMapDao.queryUserShopMap(userId, shopId);
   }
}
