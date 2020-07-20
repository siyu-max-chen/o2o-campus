package com.siyu.o2o.util;

import com.siyu.o2o.dto.UserProductMapExecution;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.entity.UserProductMap;
import com.siyu.o2o.exceptions.UserProductMapOperationException;
import com.siyu.o2o.service.PersonInfoService;
import com.siyu.o2o.service.ProductService;
import com.siyu.o2o.service.UserProductMapService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SellAnalogUtil {

   public static ProductService productService;
   public static PersonInfoService personInfoService;
   public static UserProductMapService userProductMapService;
   static List staffIds;
   static List customerIds;
   static List staffs;
   static List customers;
   static List productList;
   static Product[] productArray;
   static Random rand = new Random();


   private static void randomBuyProduct(PersonInfo customer, PersonInfo staff, int maxCount) {
      UserProductMap upm = new UserProductMap();
      upm.setCreateTime(new Date());
      upm.setOperator(staff);
      upm.setUser(customer);
      int L = productArray.length;
      maxCount = Math.min(maxCount, L);

      for(int i = 0; i < maxCount; ++i) {
         int idx = rand.nextInt(L);
         Product product = productArray[idx];
         System.out.println(idx + "/" + L + " " + product.getProductName() + " " + product.getShop().getShopId() + " " + product.getPoint());
         upm.setProduct(product);
         upm.setShop(product.getShop());
         upm.setPoint(product.getPoint());
         if(idx != L - 1) {
            Product e = productArray[idx];
            productArray[idx] = productArray[L - 1];
            productArray[L - 1] = e;
         }

         --L;

         try {
            UserProductMapExecution var10 = userProductMapService.addUserProductMap(upm);
         } catch (UserProductMapOperationException var9) {
            var9.printStackTrace();
         }
      }

   }

   @Scheduled(
      cron = "0 0 0-8 * * ?"
   )
   private static void init() {
      System.out.println("*****************************************");
      System.out.println("***************** 模拟购买行为 ************************");
      Product productCondition = new Product();
      productCondition.setEnableStatus(Integer.valueOf(1));
      productList = productService.getProductList(productCondition, 0, 1000).getProductList();
      productArray = new Product[productList.size()];

      int i;
      for(i = 0; i < productList.size(); ++i) {
         productArray[i] = (Product)productList.get(i);
      }

      staffIds = Arrays.asList(new Long[]{Long.valueOf(1L), Long.valueOf(7L), Long.valueOf(8L)});
      staffs = new ArrayList();
      Iterator var3 = staffIds.iterator();

      Long count;
      while(var3.hasNext()) {
         count = (Long)var3.next();
         staffs.add(personInfoService.getPersonInfoById(count));
      }

      customerIds = Arrays.asList(new Long[]{Long.valueOf(1L), Long.valueOf(7L), Long.valueOf(8L)});
      customers = new ArrayList();
      var3 = customerIds.iterator();

      while(var3.hasNext()) {
         count = (Long)var3.next();
         customers.add(personInfoService.getPersonInfoById(count));
      }

      System.out.println("********************************");
      System.out.println("All Customers: " + customers.size());
      System.out.println("All Staffs: " + staffs.size());
      System.out.println("All Products: " + productList.size());

      for(i = 0; i < customers.size(); ++i) {
         int var4 = Math.max(1, rand.nextInt(3));
         randomBuyProduct((PersonInfo)customers.get(i), (PersonInfo)staffs.get(rand.nextInt(staffs.size())), var4);
      }

   }

   public static void main(String[] args) {
      System.out.println("Starting the test........");
      Product productCondition = new Product();
      productCondition.setEnableStatus(Integer.valueOf(1));
      productList = productService.getProductList(productCondition, 0, 1000).getProductList();
      int count = 0;
      Iterator var3 = productList.iterator();

      while(var3.hasNext()) {
         Product product = (Product)var3.next();
         if(count++ > 10) {
            break;
         }

         System.out.println(product.getProductName());
      }

   }

}
