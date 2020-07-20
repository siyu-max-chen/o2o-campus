package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.UserProductMapDao;
import com.siyu.o2o.dao.UserShopMapDao;
import com.siyu.o2o.dto.UserProductMapExecution;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.UserProductMap;
import com.siyu.o2o.entity.UserShopMap;
import com.siyu.o2o.enums.UserProductMapStateEnum;
import com.siyu.o2o.exceptions.UserProductMapOperationException;
import com.siyu.o2o.service.UserProductMapService;
import com.siyu.o2o.util.PageCalculator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProductMapServiceImpl implements UserProductMapService {

   @Autowired
   private UserProductMapDao userProductMapDao;
   @Autowired
   private UserShopMapDao userShopMapDao;


   public UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex, Integer pageSize) {
      if(userProductCondition != null && pageIndex != null && pageSize != null) {
         int beginIndex = PageCalculator.calculateRowIndex(pageIndex.intValue(), pageSize.intValue());
         List userProductMapList = this.userProductMapDao.queryUserProductMapList(userProductCondition, beginIndex, pageSize.intValue());
         int count = this.userProductMapDao.queryUserProductMapCount(userProductCondition);
         UserProductMapExecution se = new UserProductMapExecution();
         se.setUserProductMapList(userProductMapList);
         se.setCount(Integer.valueOf(count));
         return se;
      } else {
         return null;
      }
   }

   @Transactional
   public UserProductMapExecution addUserProductMap(UserProductMap userProductMap) throws UserProductMapOperationException {
      if(userProductMap != null && userProductMap.getUser().getUserId() != null && userProductMap.getShop().getShopId() != null && userProductMap.getOperator().getUserId() != null) {
         userProductMap.setCreateTime(new Date());

         try {
            int e = this.userProductMapDao.insertUserProductMap(userProductMap);
            if(e <= 0) {
               throw new UserProductMapOperationException("添加消费记录失败");
            } else {
               if(userProductMap.getPoint() != null && userProductMap.getPoint().intValue() > 0) {
                  UserShopMap userShopMap = this.userShopMapDao.queryUserShopMap(userProductMap.getUser().getUserId().longValue(), userProductMap.getShop().getShopId().longValue());
                  if(userShopMap != null && userShopMap.getUserShopId() != null) {
                     userShopMap.setPoint(Integer.valueOf(userShopMap.getPoint().intValue() + userProductMap.getPoint().intValue()));
                     e = this.userShopMapDao.updateUserShopMapPoint(userShopMap);
                     if(e <= 0) {
                        throw new UserProductMapOperationException("更新积分信息失败");
                     }
                  } else {
                     userShopMap = this.compactUserShopMap4Add(userProductMap.getUser().getUserId(), userProductMap.getShop().getShopId(), userProductMap.getPoint());
                     e = this.userShopMapDao.insertUserShopMap(userShopMap);
                     if(e <= 0) {
                        throw new UserProductMapOperationException("积分信息创建失败");
                     }
                  }
               }

               return new UserProductMapExecution(UserProductMapStateEnum.SUCCESS, userProductMap);
            }
         } catch (Exception var4) {
            throw new UserProductMapOperationException("添加授权失败:" + var4.toString());
         }
      } else {
         return new UserProductMapExecution(UserProductMapStateEnum.NULL_USERPRODUCT_INFO);
      }
   }

   private UserShopMap compactUserShopMap4Add(Long userId, Long shopId, Integer point) {
      UserShopMap userShopMap = null;
      if(userId != null && shopId != null) {
         userShopMap = new UserShopMap();
         PersonInfo customer = new PersonInfo();
         customer.setUserId(userId);
         Shop shop = new Shop();
         shop.setShopId(shopId);
         userShopMap.setUser(customer);
         userShopMap.setShop(shop);
         userShopMap.setCreateTime(new Date());
         userShopMap.setPoint(point);
      }

      return userShopMap;
   }
}
