package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.ProductCategoryDao;
import com.siyu.o2o.dao.ProductDao;
import com.siyu.o2o.dto.ProductCategoryExecution;
import com.siyu.o2o.enums.ProductCategoryStateEnum;
import com.siyu.o2o.exceptions.ProductCategoryOperationException;
import com.siyu.o2o.service.ProductCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

   @Autowired
   private ProductCategoryDao productCategoryDao;
   @Autowired
   private ProductDao productDao;


   public List getProductCategoryList(long shopId) {
      return this.productCategoryDao.queryProductCategoryList(shopId);
   }

   @Transactional
   public ProductCategoryExecution batchAddProductCategory(List productCategoryList) throws ProductCategoryOperationException {
      if(productCategoryList != null && productCategoryList.size() > 0) {
         try {
            int e = this.productCategoryDao.batchInsertProductCategory(productCategoryList);
            if(e <= 0) {
               throw new ProductCategoryOperationException("店铺类别创建失败");
            } else {
               return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
         } catch (Exception var3) {
            throw new ProductCategoryOperationException("batchAddProductCategory error: " + var3.getMessage());
         }
      } else {
         return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
      }
   }

   @Transactional
   public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
      int e;
      try {
         e = this.productDao.updateProductCategoryToNull(productCategoryId);
         if(e < 0) {
            throw new ProductCategoryOperationException("商品类别更新失败");
         }
      } catch (Exception var7) {
         throw new ProductCategoryOperationException("deleteProductCategory error: " + var7.getMessage());
      }

      try {
         e = this.productCategoryDao.deleteProductCategory(productCategoryId, shopId);
         if(e <= 0) {
            throw new ProductCategoryOperationException("商品类别删除失败");
         } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
         }
      } catch (Exception var6) {
         throw new ProductCategoryOperationException("deleteProductCategory error:" + var6.getMessage());
      }
   }
}
