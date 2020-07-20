package com.siyu.o2o.util;

import com.siyu.o2o.cache.JedisPoolWriper;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

public class JedisUtil {

   private final int expire = 3600;
   public JedisUtil.Keys KEYS;
   public JedisUtil.Strings STRINGS;
   private JedisPool jedisPool;


   public JedisPool getJedisPool() {
      return this.jedisPool;
   }

   public void setJedisPool(JedisPoolWriper jedisPoolWriper) {
      this.jedisPool = jedisPoolWriper.getJedisPool();
   }

   public Jedis getJedis() {
      return this.jedisPool.getResource();
   }

   public class Strings {

      public String get(String key) {
         Jedis sjedis = JedisUtil.this.getJedis();
         String value = sjedis.get(key);
         sjedis.close();
         return value;
      }

      public String set(String key, String value) {
         return this.set(SafeEncoder.encode(key), SafeEncoder.encode(value));
      }

      public String set(byte[] key, byte[] value) {
         Jedis jedis = JedisUtil.this.getJedis();
         String status = jedis.set(key, value);
         jedis.close();
         return status;
      }
   }

   public class Keys {

      public String flushAll() {
         Jedis jedis = JedisUtil.this.getJedis();
         String stata = jedis.flushAll();
         jedis.close();
         return stata;
      }

      public long del(String ... keys) {
         Jedis jedis = JedisUtil.this.getJedis();
         long count = jedis.del(keys).longValue();
         jedis.close();
         return count;
      }

      public boolean exists(String key) {
         Jedis sjedis = JedisUtil.this.getJedis();
         boolean exis = sjedis.exists(key).booleanValue();
         sjedis.close();
         return exis;
      }

      public Set keys(String pattern) {
         Jedis jedis = JedisUtil.this.getJedis();
         Set set = jedis.keys(pattern);
         jedis.close();
         return set;
      }
   }
}
