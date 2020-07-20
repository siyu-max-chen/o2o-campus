package com.siyu.o2o.service.impl;

import com.siyu.o2o.util.JedisUtil;
import com.siyu.o2o.service.CacheService;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

   @Autowired
   private JedisUtil.Keys jedisKeys;


   public void removeFromCache(String keyPrefix) {
      Set keySet = this.jedisKeys.keys(keyPrefix + "*");
      Iterator var3 = keySet.iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         this.jedisKeys.del(new String[]{key});
      }

   }
}
