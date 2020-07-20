package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.ShopAuthMapDao;
import com.siyu.o2o.dao.ShopDao;
import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.dto.ShopExecution;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.entity.ShopAuthMap;
import com.siyu.o2o.enums.ShopStateEnum;
import com.siyu.o2o.exceptions.ShopOperationException;
import com.siyu.o2o.service.ShopService;
import com.siyu.o2o.util.ImageUtil;
import com.siyu.o2o.util.PageCalculator;
import com.siyu.o2o.util.PathUtil;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl implements ShopService {

   @Autowired
   private ShopDao shopDao;
   @Autowired
   private ShopAuthMapDao shopAuthMapDao;
   private static final Logger LOG = LoggerFactory.getLogger(ShopServiceImpl.class);


   public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
      int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
      List shopList = this.shopDao.queryShopList(shopCondition, rowIndex, pageSize);
      int count = this.shopDao.queryShopCount(shopCondition);
      ShopExecution se = new ShopExecution();
      if(shopList != null) {
         se.setShopList(shopList);
         se.setCount(count);
      } else {
         se.setState(ShopStateEnum.INNER_ERROR.getState());
      }

      return se;
   }

   public Shop getByShopId(long shopId) {
      return this.shopDao.queryByShopId(shopId);
   }

   @Transactional
   public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
      if(shop != null && shop.getShopId() != null) {
         try {
            if(thumbnail != null && thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
               Shop e = this.shopDao.queryByShopId(shop.getShopId().longValue());
               if(e.getShopImg() != null) {
                  ImageUtil.deleteFileOrPath(e.getShopImg());
               }

               this.addShopImg(shop, thumbnail);
            }

            shop.setLastEditTime(new Date());
            int e1 = this.shopDao.updateShop(shop);
            if(e1 <= 0) {
               return new ShopExecution(ShopStateEnum.INNER_ERROR);
            } else {
               shop = this.shopDao.queryByShopId(shop.getShopId().longValue());
               return new ShopExecution(ShopStateEnum.SUCCESS, shop);
            }
         } catch (Exception var4) {
            throw new ShopOperationException("modifyShop error:" + var4.getMessage());
         }
      } else {
         return new ShopExecution(ShopStateEnum.NULL_SHOP);
      }
   }

   @Transactional
   public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
      if(shop == null) {
         return new ShopExecution(ShopStateEnum.NULL_SHOP);
      } else {
         try {
            shop.setEnableStatus(Integer.valueOf(0));
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            shop.setRatingSum(Long.valueOf(0L));
            shop.setRatingCount(Long.valueOf(0L));
            shop.setLikes(Long.valueOf(0L));
            int e = this.shopDao.insertShop(shop);
            if(e <= 0) {
               LOG.error("插入店铺信息的时候，返回了0条变更");
               throw new ShopOperationException("店铺创建失败");
            }

            if(thumbnail.getImage() != null) {
               try {
                  this.addShopImg(shop, thumbnail);
               } catch (Exception var7) {
                  LOG.error("addShopImg error:" + var7.getMessage());
                  throw new ShopOperationException("添加店铺图片失败");
               }

               e = this.shopDao.updateShop(shop);
               if(e <= 0) {
                  LOG.error("更新图片地址失败");
                  throw new ShopOperationException("添加店铺图片失败");
               }

               ShopAuthMap shopAuthMap = new ShopAuthMap();
               shopAuthMap.setEmployee(shop.getOwner());
               shopAuthMap.setShop(shop);
               shopAuthMap.setTitle("店家");
               shopAuthMap.setTitleFlag(Integer.valueOf(0));
               shopAuthMap.setCreateTime(new Date());
               shopAuthMap.setLastEditTime(new Date());
               shopAuthMap.setEnableStatus(Integer.valueOf(1));

               try {
                  e = this.shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                  if(e <= 0) {
                     LOG.error("addShop:授权创建失败");
                     throw new ShopOperationException("授权创建失败");
                  }
               } catch (Exception var6) {
                  LOG.error("insertShopAuthMap error: " + var6.getMessage());
                  throw new ShopOperationException("授权创建失败");
               }
            }
         } catch (Exception var8) {
            LOG.error("addShop error:" + var8.getMessage());
            throw new ShopOperationException("创建店铺失败，请联系相关管理员");
         }

         return new ShopExecution(ShopStateEnum.CHECK, shop);
      }
   }

   private void addShopImg(Shop shop, ImageHolder thumbnail) {
      String dest = PathUtil.getShopImagePath(shop.getShopId().longValue());
      String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
      shop.setShopImg(shopImgAddr);
   }

}
