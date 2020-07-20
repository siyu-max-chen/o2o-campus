package com.siyu.o2o.config.quartz;

import com.siyu.o2o.service.ProductSellDailyService;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

   @Autowired
   private ProductSellDailyService productSellDailyService;
   @Autowired
   private MethodInvokingJobDetailFactoryBean jobDetailFactory;
   @Autowired
   private CronTriggerFactoryBean productSellDailyTriggerFactory;


   @Bean(
      name = {"jobDetailFacotry"}
   )
   public MethodInvokingJobDetailFactoryBean createJobDetail() {
      MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
      jobDetailFactoryBean.setName("product_sell_daily_job");
      jobDetailFactoryBean.setGroup("job_product_sell_daily_group");
      jobDetailFactoryBean.setConcurrent(false);
      jobDetailFactoryBean.setTargetObject(this.productSellDailyService);
      jobDetailFactoryBean.setTargetMethod("dailyCalculate");
      return jobDetailFactoryBean;
   }

   @Bean({"productSellDailyTriggerFactory"})
   public CronTriggerFactoryBean createProductSellDailyTrigger() {
      CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
      triggerFactory.setName("product_sell_daily_trigger");
      triggerFactory.setGroup("job_product_sell_daily_group");
      triggerFactory.setJobDetail(this.jobDetailFactory.getObject());
      triggerFactory.setCronExpression("0 0 0 * * ? *");
      return triggerFactory;
   }

   @Bean({"schedulerFactory"})
   public SchedulerFactoryBean createSchedulerFactory() {
      SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
      schedulerFactory.setTriggers(new Trigger[]{this.productSellDailyTriggerFactory.getObject()});
      return schedulerFactory;
   }
}
