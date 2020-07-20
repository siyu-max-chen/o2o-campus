package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.AwardDao;
import com.siyu.o2o.dto.AwardExecution;
import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.entity.Award;
import com.siyu.o2o.enums.AwardStateEnum;
import com.siyu.o2o.exceptions.AwardOperationException;
import com.siyu.o2o.service.AwardService;
import com.siyu.o2o.util.ImageUtil;
import com.siyu.o2o.util.PageCalculator;
import com.siyu.o2o.util.PathUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AwardServiceImpl implements AwardService {

   @Autowired
   private AwardDao awardDao;


   public AwardExecution getAwardList(Award awardCondition, int pageIndex, int pageSize) {
      int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
      List awardList = this.awardDao.queryAwardList(awardCondition, rowIndex, pageSize);
      int count = this.awardDao.queryAwardCount(awardCondition);
      AwardExecution ae = new AwardExecution();
      ae.setAwardList(awardList);
      ae.setCount(count);
      return ae;
   }

   public Award getAwardById(long awardId) {
      return this.awardDao.queryAwardByAwardId(awardId);
   }

   @Transactional
   public AwardExecution addAward(Award award, ImageHolder thumbnail) {
      if(award != null && award.getShopId() != null) {
         award.setCreateTime(new Date());
         award.setLastEditTime(new Date());
         award.setEnableStatus(Integer.valueOf(1));
         if(thumbnail != null) {
            this.addThumbnail(award, thumbnail);
         }

         try {
            int e = this.awardDao.insertAward(award);
            if(e <= 0) {
               throw new AwardOperationException("创建商品失败");
            }
         } catch (Exception var4) {
            throw new AwardOperationException("创建商品失败:" + var4.toString());
         }

         return new AwardExecution(AwardStateEnum.SUCCESS, award);
      } else {
         return new AwardExecution(AwardStateEnum.EMPTY);
      }
   }

   @Transactional
   public AwardExecution modifyAward(Award award, ImageHolder thumbnail) {
      if(award != null && award.getAwardId() != null) {
         award.setLastEditTime(new Date());
         if(thumbnail != null) {
            Award e = this.awardDao.queryAwardByAwardId(award.getAwardId().longValue());
            if(e.getAwardImg() != null) {
               ImageUtil.deleteFileOrPath(e.getAwardImg());
            }

            this.addThumbnail(award, thumbnail);
         }

         try {
            int e1 = this.awardDao.updateAward(award);
            if(e1 <= 0) {
               throw new AwardOperationException("更新商品信息失败");
            } else {
               return new AwardExecution(AwardStateEnum.SUCCESS, award);
            }
         } catch (Exception var4) {
            throw new AwardOperationException("更新商品信息失败:" + var4.toString());
         }
      } else {
         return new AwardExecution(AwardStateEnum.EMPTY);
      }
   }

   private void addThumbnail(Award award, ImageHolder thumbnail) {
      String dest = PathUtil.getShopImagePath(award.getShopId().longValue());
      String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
      award.setAwardImg(thumbnailAddr);
   }
}
