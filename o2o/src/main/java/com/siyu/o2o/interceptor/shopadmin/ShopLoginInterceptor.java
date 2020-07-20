package com.siyu.o2o.interceptor.shopadmin;

import com.siyu.o2o.entity.PersonInfo;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      Object userObj = request.getSession().getAttribute("user");
      if(userObj != null) {
         PersonInfo out = (PersonInfo)userObj;
         if(out != null && out.getUserId() != null && out.getUserId().longValue() > 0L && out.getEnableStatus().intValue() == 1) {
            return true;
         }
      }

      PrintWriter out1 = response.getWriter();
      out1.println("<html>");
      out1.println("<script>");
      out1.println("window.open (\'" + request.getContextPath() + "/local/login?usertype=2\',\'_self\')");
      out1.println("</script>");
      out1.println("</html>");
      return false;
   }
}
