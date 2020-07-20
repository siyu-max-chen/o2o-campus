package com.siyu.o2o.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.util.JedisUtil;
import com.siyu.o2o.dao.HeadLineDao;
import com.siyu.o2o.dao.ShopDao;
import com.siyu.o2o.entity.HeadLine;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.exceptions.HeadLineOperationException;
import com.siyu.o2o.service.HeadLineService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HeadLineServiceImpl implements HeadLineService {

   @Autowired
   private HeadLineDao headLineDao;
   @Autowired
   private ShopDao shopDao;
   @Autowired
   private JedisUtil.Keys jedisKeys;
   @Autowired
   private JedisUtil.Strings jedisStrings;
   private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);


   @Transactional
   public List getHeadLineList(HeadLine headLineCondition) {
      String key = "headlinelist";
      List headLineList = null;
      ObjectMapper mapper = new ObjectMapper();
      if(headLineCondition != null && headLineCondition.getEnableStatus() != null) {
         key = key + "_" + headLineCondition.getEnableStatus();
      }

      String jsonString;
      if(!this.jedisKeys.exists(key)) {
         headLineList = this.headLineDao.queryHeadLine(headLineCondition);

         try {
            jsonString = mapper.writeValueAsString(headLineList);
         } catch (JsonProcessingException var11) {
            var11.printStackTrace();
            logger.error(var11.getMessage());
            throw new HeadLineOperationException(var11.getMessage());
         }

         this.jedisStrings.set(key, jsonString);
      } else {
         jsonString = this.jedisStrings.get(key);
         JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{HeadLine.class});

         try {
            headLineList = (List)mapper.readValue(jsonString, javaType);
         } catch (JsonParseException var8) {
            var8.printStackTrace();
            logger.error(var8.getMessage());
            throw new HeadLineOperationException(var8.getMessage());
         } catch (JsonMappingException var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            throw new HeadLineOperationException(var9.getMessage());
         } catch (IOException var10) {
            var10.printStackTrace();
            logger.error(var10.getMessage());
            throw new HeadLineOperationException(var10.getMessage());
         }
      }

      return headLineList;
   }

   @Transactional
   public List getBestShopList() {
      String key = "headlinelist";
      List bestShopList = null;
      ObjectMapper mapper = new ObjectMapper();
      key = key + "_best_five_shop";
      String jsonString;
      if(!this.jedisKeys.exists(key)) {
         bestShopList = this.shopDao.queryTop5ShopIdList();

         try {
            jsonString = mapper.writeValueAsString(bestShopList);
         } catch (JsonProcessingException var10) {
            var10.printStackTrace();
            logger.error(var10.getMessage());
            throw new HeadLineOperationException(var10.getMessage());
         }

         this.jedisStrings.set(key, jsonString);
      } else {
         jsonString = this.jedisStrings.get(key);
         JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{Shop.class});

         try {
            bestShopList = (List)mapper.readValue(jsonString, javaType);
         } catch (JsonParseException var7) {
            var7.printStackTrace();
            logger.error(var7.getMessage());
            throw new HeadLineOperationException(var7.getMessage());
         } catch (JsonMappingException var8) {
            var8.printStackTrace();
            logger.error(var8.getMessage());
            throw new HeadLineOperationException(var8.getMessage());
         } catch (IOException var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            throw new HeadLineOperationException(var9.getMessage());
         }
      }

      return bestShopList;
   }

}
