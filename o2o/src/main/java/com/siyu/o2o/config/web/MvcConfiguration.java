package com.siyu.o2o.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.siyu.o2o.interceptor.shopadmin.ShopLoginInterceptor;
import com.siyu.o2o.interceptor.shopadmin.ShopPermissionInterceptor;
import javax.servlet.ServletException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {

   private ApplicationContext applicationContext;
   @Value("${kaptcha.border}")
   private String border;
   @Value("${kaptcha.textproducer.font.color}")
   private String fcolor;
   @Value("${kaptcha.image.width}")
   private String width;
   @Value("${kaptcha.textproducer.char.string}")
   private String cString;
   @Value("${kaptcha.image.height}")
   private String height;
   @Value("${kaptcha.textproducer.font.size}")
   private String fsize;
   @Value("${kaptcha.noise.color}")
   private String nColor;
   @Value("${kaptcha.textproducer.char.length}")
   private String clength;
   @Value("${kaptcha.textproducer.font.names}")
   private String fnames;


   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = applicationContext;
   }

   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler(new String[]{"/upload/**"}).addResourceLocations(new String[]{"file:/home/ec2-user/work/upload/"});
   }

   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
   }

   @Bean(
      name = {"viewResolver"}
   )
   public ViewResolver createViewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setApplicationContext(this.applicationContext);
      viewResolver.setCache(false);
      viewResolver.setPrefix("/WEB-INF/html/");
      viewResolver.setSuffix(".html");
      return viewResolver;
   }

   @Bean(
      name = {"multipartResolver"}
   )
   public CommonsMultipartResolver createMultipartResolver() {
      CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
      multipartResolver.setDefaultEncoding("utf-8");
      multipartResolver.setMaxUploadSize(20971520L);
      multipartResolver.setMaxInMemorySize(20971520);
      return multipartResolver;
   }

   @Bean
   public ServletRegistrationBean servletRegistrationBean() throws ServletException {
      ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), new String[]{"/Kaptcha"});
      servlet.addInitParameter("kaptcha.border", this.border);
      servlet.addInitParameter("kaptcha.textproducer.font.color", this.fcolor);
      servlet.addInitParameter("kaptcha.image.width", this.width);
      servlet.addInitParameter("kaptcha.textproducer.char.string", this.cString);
      servlet.addInitParameter("kaptcha.image.height", this.height);
      servlet.addInitParameter("kaptcha.textproducer.font.size", this.fsize);
      servlet.addInitParameter("kaptcha.noise.color", this.nColor);
      servlet.addInitParameter("kaptcha.textproducer.char.length", this.clength);
      servlet.addInitParameter("kaptcha.textproducer.font.names", this.fnames);
      return servlet;
   }

   public void addInterceptors(InterceptorRegistry registry) {
      String interceptPath = "/shopadmin/**";
      InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
      loginIR.addPathPatterns(new String[]{interceptPath});
      loginIR.excludePathPatterns(new String[]{"/shopadmin/addshopauthmap"});
      loginIR.excludePathPatterns(new String[]{"/shopadmin/adduserproductmap"});
      loginIR.excludePathPatterns(new String[]{"/shopadmin/exchangeaward"});
      InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
      permissionIR.addPathPatterns(new String[]{interceptPath});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/shoplist"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/getshoplist"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/getshopinitinfo"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/registershop"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/shopoperation"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/shopmanagement"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/getshopmanagementinfo"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/addshopauthmap"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/adduserproductmap"});
      permissionIR.excludePathPatterns(new String[]{"/shopadmin/exchangeaward"});
   }
}
