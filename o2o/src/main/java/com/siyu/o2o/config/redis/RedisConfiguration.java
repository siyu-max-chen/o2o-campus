package com.siyu.o2o.config.redis;

import com.siyu.o2o.cache.JedisPoolWriper;
import com.siyu.o2o.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

   @Value("${redis.hostname}")
   private String hostname;
   @Value("${redis.port}")
   private int port;
   @Value("${redis.password}")
   private String password;
   @Value("${redis.pool.maxActive}")
   private int maxTotal;
   @Value("${redis.pool.maxIdle}")
   private int maxIdle;
   @Value("${redis.pool.maxWait}")
   private long maxWaitMillis;
   @Value("${redis.pool.testOnBorrow}")
   private boolean testOnBorrow;
   @Autowired
   private JedisPoolConfig jedisPoolConfig;
   @Autowired
   private JedisPoolWriper jedisWritePool;
   @Autowired
   private JedisUtil jedisUtil;


   @Bean(
      name = {"jedisPoolConfig"}
   )
   public JedisPoolConfig createJedisPoolConfig() {
      JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
      jedisPoolConfig.setMaxTotal(this.maxTotal);
      jedisPoolConfig.setMaxIdle(this.maxIdle);
      jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);
      jedisPoolConfig.setTestOnBorrow(this.testOnBorrow);
      return jedisPoolConfig;
   }

   @Bean(
      name = {"jedisWritePool"}
   )
   public JedisPoolWriper createJedisPoolWriper() {
      JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(this.jedisPoolConfig, this.hostname, this.port, this.password);
      return jedisPoolWriper;
   }

   @Bean(
      name = {"jedisUtil"}
   )
   public JedisUtil createJedisUtil() {
      JedisUtil jedisUtil = new JedisUtil();
      jedisUtil.setJedisPool(this.jedisWritePool);
      return jedisUtil;
   }

   @Bean(
      name = {"jedisKeys"}
   )
   public JedisUtil.Keys createJedisKeys() {
      JedisUtil.Keys var10000 = new JedisUtil.Keys;
      JedisUtil var10002 = this.jedisUtil;
      this.jedisUtil.getClass();
      var10000.<init>();
      JedisUtil.Keys jedisKeys = var10000;
      return jedisKeys;
   }

   @Bean(
      name = {"jedisStrings"}
   )
   public JedisUtil.Strings createJedisStrings() {
      JedisUtil.Strings var10000 = new JedisUtil.Strings;
      JedisUtil var10002 = this.jedisUtil;
      this.jedisUtil.getClass();
      var10000.<init>();
      JedisUtil.Strings jedisStrings = var10000;
      return jedisStrings;
   }
}
