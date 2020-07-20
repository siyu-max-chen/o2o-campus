package com.siyu.o2o.web.frontend;

import com.siyu.o2o.dto.AwardExecution;
import com.siyu.o2o.entity.Award;
import com.siyu.o2o.entity.PersonInfo;
import com.siyu.o2o.entity.UserShopMap;
import com.siyu.o2o.service.AwardService;
import com.siyu.o2o.service.UserShopMapService;
import com.siyu.o2o.util.HttpServletRequestUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/frontend"})
public class ShopAwardController {

   @Autowired
   private AwardService awardService;
   @Autowired
   private UserShopMapService userShopMapService;


   @RequestMapping(
      value = {"/listawardsbyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listAwardsByShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      long shopId = HttpServletRequestUtil.getLong(request, "shopId");
      if(pageIndex > -1 && pageSize > -1 && shopId > -1L) {
         String awardName = HttpServletRequestUtil.getString(request, "awardName");
         Award awardCondition = this.compactAwardCondition4Search(shopId, awardName);
         AwardExecution ae = this.awardService.getAwardList(awardCondition, pageIndex, pageSize);
         modelMap.put("awardList", ae.getAwardList());
         modelMap.put("count", Integer.valueOf(ae.getCount()));
         modelMap.put("success", Boolean.valueOf(true));
         PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
         if(user != null && user.getUserId() != null) {
            UserShopMap userShopMap = this.userShopMapService.getUserShopMap(user.getUserId().longValue(), shopId);
            if(userShopMap == null) {
               modelMap.put("totalPoint", Integer.valueOf(0));
            } else {
               modelMap.put("totalPoint", userShopMap.getPoint());
            }
         }
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
      }

      return modelMap;
   }

   private Award compactAwardCondition4Search(long shopId, String awardName) {
      Award awardCondition = new Award();
      awardCondition.setShopId(Long.valueOf(shopId));
      awardCondition.setEnableStatus(Integer.valueOf(1));
      if(awardName != null) {
         awardCondition.setAwardName(awardName);
      }

      return awardCondition;
   }
}
