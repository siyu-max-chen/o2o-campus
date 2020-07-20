package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.ShopAuthMapDao;
import com.siyu.o2o.dto.ShopAuthMapExecution;
import com.siyu.o2o.entity.ShopAuthMap;
import com.siyu.o2o.enums.ShopAuthMapStateEnum;
import com.siyu.o2o.exceptions.ShopAuthMapOperationException;
import com.siyu.o2o.service.ShopAuthMapService;
import com.siyu.o2o.util.PageCalculator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {

   @Autowired
   private ShopAuthMapDao shopAuthMapDao;


   public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
      if(shopId != null && pageIndex != null && pageSize != null) {
         int beginIndex = PageCalculator.calculateRowIndex(pageIndex.intValue(), pageSize.intValue());
         List shopAuthMapList = this.shopAuthMapDao.queryShopAuthMapListByShopId(shopId.longValue(), beginIndex, pageSize.intValue());
         int count = this.shopAuthMapDao.queryShopAuthCountByShopId(shopId.longValue());
         ShopAuthMapExecution se = new ShopAuthMapExecution();
         se.setShopAuthMapList(shopAuthMapList);
         se.setCount(Integer.valueOf(count));
         return se;
      } else {
         return null;
      }
   }

   public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
      return this.shopAuthMapDao.queryShopAuthMapById(shopAuthId);
   }

   @Transactional
   public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
      if(shopAuthMap != null && shopAuthMap.getShop() != null && shopAuthMap.getShop().getShopId() != null && shopAuthMap.getEmployee() != null && shopAuthMap.getEmployee().getUserId() != null) {
         shopAuthMap.setCreateTime(new Date());
         shopAuthMap.setLastEditTime(new Date());
         shopAuthMap.setEnableStatus(Integer.valueOf(1));

         try {
            int e = this.shopAuthMapDao.insertShopAuthMap(shopAuthMap);
            if(e <= 0) {
               throw new ShopAuthMapOperationException("添加授权失败");
            } else {
               return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
            }
         } catch (Exception var3) {
            throw new ShopAuthMapOperationException("添加授权失败:" + var3.toString());
         }
      } else {
         return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
      }
   }

   @Transactional
   public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
      if(shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
         try {
            shopAuthMap.setLastEditTime(new Date());
            int e = this.shopAuthMapDao.updateShopAuthMap(shopAuthMap);
            return e <= 0?new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR):new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
         } catch (Exception var3) {
            throw new ShopAuthMapOperationException("modifyShopAuthMap error: " + var3.getMessage());
         }
      } else {
         return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
      }
   }
}
