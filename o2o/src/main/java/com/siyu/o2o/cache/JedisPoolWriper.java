package com.siyu.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolWriper {

   private JedisPool jedisPool;


   public JedisPoolWriper(JedisPoolConfig poolConfig, String host, int port, String password) {
      try {
         this.jedisPool = new JedisPool(poolConfig, host, port, 1000, password);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public JedisPool getJedisPool() {
      return this.jedisPool;
   }

   public void setJedisPool(JedisPool jedisPool) {
      this.jedisPool = jedisPool;
   }
}
