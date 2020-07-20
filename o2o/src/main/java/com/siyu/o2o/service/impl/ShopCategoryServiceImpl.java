package com.siyu.o2o.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.util.JedisUtil;
import com.siyu.o2o.dao.ShopCategoryDao;
import com.siyu.o2o.entity.ShopCategory;
import com.siyu.o2o.exceptions.ShopCategoryOperationException;
import com.siyu.o2o.service.ShopCategoryService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

   @Autowired
   private ShopCategoryDao shopCategoryDao;
   @Autowired
   private JedisUtil.Keys jedisKeys;
   @Autowired
   private JedisUtil.Strings jedisStrings;
   private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);


   public List getShopCategoryList(ShopCategory shopCategoryCondition) {
      String key = "shopcategorylist";
      List shopCategoryList = null;
      ObjectMapper mapper = new ObjectMapper();
      if(shopCategoryCondition == null) {
         key = key + "_allfirstlevel";
      } else if(shopCategoryCondition != null && shopCategoryCondition.getParent() != null && shopCategoryCondition.getParent().getShopCategoryId() != null) {
         key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
      } else if(shopCategoryCondition != null) {
         key = key + "_allsecondlevel";
      }

      String jsonString;
      if(!this.jedisKeys.exists(key)) {
         shopCategoryList = this.shopCategoryDao.queryShopCategory(shopCategoryCondition);

         try {
            jsonString = mapper.writeValueAsString(shopCategoryList);
         } catch (JsonProcessingException var11) {
            var11.printStackTrace();
            logger.error(var11.getMessage());
            throw new ShopCategoryOperationException(var11.getMessage());
         }

         this.jedisStrings.set(key, jsonString);
      } else {
         jsonString = this.jedisStrings.get(key);
         JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{ShopCategory.class});

         try {
            shopCategoryList = (List)mapper.readValue(jsonString, javaType);
         } catch (JsonParseException var8) {
            var8.printStackTrace();
            logger.error(var8.getMessage());
            throw new ShopCategoryOperationException(var8.getMessage());
         } catch (JsonMappingException var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            throw new ShopCategoryOperationException(var9.getMessage());
         } catch (IOException var10) {
            var10.printStackTrace();
            logger.error(var10.getMessage());
            throw new ShopCategoryOperationException(var10.getMessage());
         }
      }

      return this.shopCategoryDao.queryShopCategory(shopCategoryCondition);
   }

}
