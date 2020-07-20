package com.siyu.o2o.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.util.JedisUtil;
import com.siyu.o2o.dao.AreaDao;
import com.siyu.o2o.entity.Area;
import com.siyu.o2o.exceptions.AreaOperationException;
import com.siyu.o2o.service.AreaService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaServiceImpl implements AreaService {

   @Autowired
   private AreaDao areaDao;
   @Autowired
   private JedisUtil.Keys jedisKeys;
   @Autowired
   private JedisUtil.Strings jedisStrings;
   private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);


   @Transactional
   public List getAreaList() {
      String key = "arealist";
      List areaList = null;
      ObjectMapper mapper = new ObjectMapper();
      String jsonString;
      if(!this.jedisKeys.exists(key)) {
         areaList = this.areaDao.queryArea();

         try {
            jsonString = mapper.writeValueAsString(areaList);
         } catch (JsonProcessingException var10) {
            var10.printStackTrace();
            logger.error(var10.getMessage());
            throw new AreaOperationException(var10.getMessage());
         }

         this.jedisStrings.set(key, jsonString);
      } else {
         jsonString = this.jedisStrings.get(key);
         JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{Area.class});

         try {
            areaList = (List)mapper.readValue(jsonString, javaType);
         } catch (JsonParseException var7) {
            var7.printStackTrace();
            logger.error(var7.getMessage());
            throw new AreaOperationException(var7.getMessage());
         } catch (JsonMappingException var8) {
            var8.printStackTrace();
            logger.error(var8.getMessage());
            throw new AreaOperationException(var8.getMessage());
         } catch (IOException var9) {
            var9.printStackTrace();
            logger.error(var9.getMessage());
            throw new AreaOperationException(var9.getMessage());
         }
      }

      return areaList;
   }

}
