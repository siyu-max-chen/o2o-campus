package com.siyu.o2o.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.siyu.o2o.util.DESUtil;
import java.beans.PropertyVetoException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.siyu.o2o.dao"})
public class DataSourceConfiguration {

   @Value("${jdbc.driver}")
   private String jdbcDriver;
   @Value("${jdbc.url}")
   private String jdbcUrl;
   @Value("${jdbc.username}")
   private String jdbcUsername;
   @Value("${jdbc.password}")
   private String jdbcPassword;


   @Bean(
      name = {"dataSource"}
   )
   public ComboPooledDataSource createDataSource() throws PropertyVetoException {
      ComboPooledDataSource dataSource = new ComboPooledDataSource();
      dataSource.setDriverClass(this.jdbcDriver);
      dataSource.setJdbcUrl(this.jdbcUrl);
      dataSource.setUser(DESUtil.getDecryptString(this.jdbcUsername));
      dataSource.setPassword(DESUtil.getDecryptString(this.jdbcPassword));
      dataSource.setMaxPoolSize(30);
      dataSource.setMinPoolSize(10);
      dataSource.setInitialPoolSize(10);
      dataSource.setAutoCommitOnClose(false);
      dataSource.setCheckoutTimeout(10000);
      dataSource.setAcquireRetryAttempts(2);
      return dataSource;
   }
}
