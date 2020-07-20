package com.siyu.o2o.service.impl;

import com.siyu.o2o.dao.ProductDao;
import com.siyu.o2o.dao.ProductImgDao;
import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.dto.ProductExecution;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.entity.ProductImg;
import com.siyu.o2o.enums.ProductStateEnum;
import com.siyu.o2o.exceptions.ProductOperationException;
import com.siyu.o2o.service.ProductService;
import com.siyu.o2o.util.ImageUtil;
import com.siyu.o2o.util.PageCalculator;
import com.siyu.o2o.util.PathUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

   @Autowired
   private ProductDao productDao;
   @Autowired
   private ProductImgDao productImgDao;


   @Transactional
   public ProductExecution addProduct(Product product, ImageHolder thumbnail, List productImgHolderList) throws ProductOperationException {
      if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
         product.setCreateTime(new Date());
         product.setLastEditTime(new Date());
         product.setEnableStatus(Integer.valueOf(1));
         if(thumbnail != null) {
            this.addThumbnail(product, thumbnail);
         }

         try {
            int e = this.productDao.insertProduct(product);
            if(e <= 0) {
               throw new ProductOperationException("创建商品失败");
            }
         } catch (Exception var5) {
            throw new ProductOperationException("创建商品失败:" + var5.toString());
         }

         if(productImgHolderList != null && productImgHolderList.size() > 0) {
            this.addProductImgList(product, productImgHolderList);
         }

         return new ProductExecution(ProductStateEnum.SUCCESS, product);
      } else {
         return new ProductExecution(ProductStateEnum.EMPTY);
      }
   }

   private void addThumbnail(Product product, ImageHolder thumbnail) {
      String dest = PathUtil.getShopImagePath(product.getShop().getShopId().longValue());
      String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
      product.setImgAddr(thumbnailAddr);
   }

   private void addProductImgList(Product product, List productImgHolderList) {
      String dest = PathUtil.getShopImagePath(product.getShop().getShopId().longValue());
      ArrayList productImgList = new ArrayList();
      Iterator e = productImgHolderList.iterator();

      while(e.hasNext()) {
         ImageHolder productImgHolder = (ImageHolder)e.next();
         String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
         ProductImg productImg = new ProductImg();
         productImg.setImgAddr(imgAddr);
         productImg.setProductId(product.getProductId());
         productImg.setCreateTime(new Date());
         productImgList.add(productImg);
      }

      if(productImgList.size() > 0) {
         try {
            int e1 = this.productImgDao.batchInsertProductImg(productImgList);
            if(e1 <= 0) {
               throw new ProductOperationException("创建商品详情图片失败");
            }
         } catch (Exception var9) {
            throw new ProductOperationException("创建商品详情图片失败:" + var9.toString());
         }
      }

   }

   private void deleteProductImgList(long productId) {
      List productImgList = this.productImgDao.queryProductImgList(productId);
      Iterator var4 = productImgList.iterator();

      while(var4.hasNext()) {
         ProductImg productImg = (ProductImg)var4.next();
         ImageUtil.deleteFileOrPath(productImg.getImgAddr());
      }

      this.productImgDao.deleteProductImgByProductId(productId);
   }

   public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
      int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
      List productList = this.productDao.queryProductList(productCondition, rowIndex, pageSize);
      int count = this.productDao.queryProductCount(productCondition);
      ProductExecution pe = new ProductExecution();
      pe.setProductList(productList);
      pe.setCount(count);
      return pe;
   }

   public Product getProductById(long productId) {
      return this.productDao.queryProductById(productId);
   }

   @Transactional
   public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List productImgHolderList) throws ProductOperationException {
      if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
         product.setLastEditTime(new Date());
         if(thumbnail != null) {
            Product e = this.productDao.queryProductById(product.getProductId().longValue());
            if(e.getImgAddr() != null) {
               ImageUtil.deleteFileOrPath(e.getImgAddr());
            }

            this.addThumbnail(product, thumbnail);
         }

         if(productImgHolderList != null && productImgHolderList.size() > 0) {
            this.deleteProductImgList(product.getProductId().longValue());
            this.addProductImgList(product, productImgHolderList);
         }

         try {
            int e1 = this.productDao.updateProduct(product);
            if(e1 <= 0) {
               throw new ProductOperationException("更新商品信息失败");
            } else {
               return new ProductExecution(ProductStateEnum.SUCCESS, product);
            }
         } catch (Exception var5) {
            throw new ProductOperationException("更新商品信息失败:" + var5.toString());
         }
      } else {
         return new ProductExecution(ProductStateEnum.EMPTY);
      }
   }
}
