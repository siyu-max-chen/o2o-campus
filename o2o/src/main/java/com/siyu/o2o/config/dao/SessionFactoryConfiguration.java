package com.siyu.o2o.config.dao;

import java.io.IOException;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class SessionFactoryConfiguration {

   private static String mybatisConfigFile;
   private static String mapperPath;
   @Value("${type_alias_package}")
   private String typeAliasPackage;
   @Autowired
   private DataSource dataSource;


   @Value("${mybatis_config_file}")
   public void setMybatisConfigFile(String mybatisConfigFile) {
      mybatisConfigFile = mybatisConfigFile;
   }

   @Value("${mapper_path}")
   public void setMapperPath(String mapperPath) {
      mapperPath = mapperPath;
   }

   @Bean(
      name = {"sqlSessionFactory"}
   )
   public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFile));
      PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
      String packageSearchPath = "classpath*:" + mapperPath;
      sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
      sqlSessionFactoryBean.setDataSource(this.dataSource);
      sqlSessionFactoryBean.setTypeAliasesPackage(this.typeAliasPackage);
      return sqlSessionFactoryBean;
   }
}
