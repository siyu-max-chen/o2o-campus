package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.UserAwardMapDao;
import com.siyu.o2o.dao.UserShopMapDao;
import com.siyu.o2o.dto.UserAwardMapExecution;
import com.siyu.o2o.entity.UserAwardMap;
import com.siyu.o2o.entity.UserShopMap;
import com.siyu.o2o.enums.UserAwardMapStateEnum;
import com.siyu.o2o.exceptions.UserAwardMapOperationException;
import com.siyu.o2o.service.UserAwardMapService;
import com.siyu.o2o.util.PageCalculator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAwardMapServiceImpl implements UserAwardMapService {

   @Autowired
   private UserAwardMapDao userAwardMapDao;
   @Autowired
   private UserShopMapDao userShopMapDao;


   public UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardCondition, Integer pageIndex, Integer pageSize) {
      if(userAwardCondition != null && pageIndex != null && pageSize != null) {
         int beginIndex = PageCalculator.calculateRowIndex(pageIndex.intValue(), pageSize.intValue());
         List userAwardMapList = this.userAwardMapDao.queryUserAwardMapList(userAwardCondition, beginIndex, pageSize.intValue());
         int count = this.userAwardMapDao.queryUserAwardMapCount(userAwardCondition);
         UserAwardMapExecution ue = new UserAwardMapExecution();
         ue.setUserAwardMapList(userAwardMapList);
         ue.setCount(Integer.valueOf(count));
         return ue;
      } else {
         return null;
      }
   }

   public UserAwardMapExecution listReceivedUserAwardMap(UserAwardMap userAwardCondition, Integer pageIndex, Integer pageSize) {
      if(userAwardCondition != null && pageIndex != null && pageSize != null) {
         int beginIndex = PageCalculator.calculateRowIndex(pageIndex.intValue(), pageSize.intValue());
         List userAwardMapList = this.userAwardMapDao.queryReceivedUserAwardMapList(userAwardCondition, beginIndex, pageSize.intValue());
         int count = this.userAwardMapDao.queryUserAwardMapCount(userAwardCondition);
         UserAwardMapExecution ue = new UserAwardMapExecution();
         ue.setUserAwardMapList(userAwardMapList);
         ue.setCount(Integer.valueOf(count));
         return ue;
      } else {
         return null;
      }
   }

   public UserAwardMap getUserAwardMapById(long userAwardMapId) {
      return this.userAwardMapDao.queryUserAwardMapById(userAwardMapId);
   }

   @Transactional
   public UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap) throws UserAwardMapOperationException {
      if(userAwardMap != null && userAwardMap.getUser() != null && userAwardMap.getUser().getUserId() != null && userAwardMap.getShop() != null && userAwardMap.getShop().getShopId() != null) {
         userAwardMap.setCreateTime(new Date());
         userAwardMap.setUsedStatus(Integer.valueOf(0));

         try {
            boolean e = false;
            int e1;
            if(userAwardMap.getPoint() != null && userAwardMap.getPoint().intValue() > 0) {
               UserShopMap userShopMap = this.userShopMapDao.queryUserShopMap(userAwardMap.getUser().getUserId().longValue(), userAwardMap.getShop().getShopId().longValue());
               if(userShopMap == null) {
                  throw new UserAwardMapOperationException("在本店铺没有积分，无法对换奖品");
               }

               if(userShopMap.getPoint().intValue() < userAwardMap.getPoint().intValue()) {
                  throw new UserAwardMapOperationException("积分不足无法领取");
               }

               userShopMap.setPoint(Integer.valueOf(userShopMap.getPoint().intValue() - userAwardMap.getPoint().intValue()));
               e1 = this.userShopMapDao.updateUserShopMapPoint(userShopMap);
               if(e1 <= 0) {
                  throw new UserAwardMapOperationException("更新积分信息失败");
               }
            }

            e1 = this.userAwardMapDao.insertUserAwardMap(userAwardMap);
            if(e1 <= 0) {
               throw new UserAwardMapOperationException("领取奖励失败");
            } else {
               return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
            }
         } catch (Exception var4) {
            throw new UserAwardMapOperationException("领取奖励失败:" + var4.toString());
         }
      } else {
         return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_INFO);
      }
   }

   @Transactional
   public UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap) throws UserAwardMapOperationException {
      if(userAwardMap != null && userAwardMap.getUserAwardId() != null && userAwardMap.getUsedStatus() != null) {
         try {
            int e = this.userAwardMapDao.updateUserAwardMap(userAwardMap);
            return e <= 0?new UserAwardMapExecution(UserAwardMapStateEnum.INNER_ERROR):new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS, userAwardMap);
         } catch (Exception var3) {
            throw new UserAwardMapOperationException("modifyUserAwardMap error: " + var3.getMessage());
         }
      } else {
         return new UserAwardMapExecution(UserAwardMapStateEnum.NULL_USERAWARD_ID);
      }
   }
}
