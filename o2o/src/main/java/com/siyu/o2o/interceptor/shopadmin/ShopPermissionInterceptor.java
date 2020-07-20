package com.siyu.o2o.interceptor.shopadmin;

import com.siyu.o2o.entity.Shop;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      List shopList = (List)request.getSession().getAttribute("shopList");
      if(currentShop != null && shopList != null) {
         Iterator var6 = shopList.iterator();

         while(var6.hasNext()) {
            Shop shop = (Shop)var6.next();
            if(shop.getShopId() == currentShop.getShopId()) {
               return true;
            }
         }
      }

      return false;
   }
}
