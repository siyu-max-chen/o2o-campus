package com.siyu.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siyu.o2o.dto.AwardExecution;
import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.entity.Award;
import com.siyu.o2o.entity.Shop;
import com.siyu.o2o.enums.AwardStateEnum;
import com.siyu.o2o.service.AwardService;
import com.siyu.o2o.util.CodeUtil;
import com.siyu.o2o.util.HttpServletRequestUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping({"/shopadmin"})
public class AwardManagementController {

   @Autowired
   private AwardService awardService;


   @RequestMapping(
      value = {"/listawardsbyshop"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map listAwardsByShop(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
      int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
      Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
      if(pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
         String awardName = HttpServletRequestUtil.getString(request, "awardName");
         Award awardCondition = this.compactAwardCondition4Search(currentShop.getShopId().longValue(), awardName);
         AwardExecution ae = this.awardService.getAwardList(awardCondition, pageIndex, pageSize);
         modelMap.put("awardList", ae.getAwardList());
         modelMap.put("count", Integer.valueOf(ae.getCount()));
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/getawardbyid"},
      method = {RequestMethod.GET}
   )
   @ResponseBody
   private Map getAwardbyId(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      long awardId = HttpServletRequestUtil.getLong(request, "awardId");
      if(awardId > -1L) {
         Award award = this.awardService.getAwardById(awardId);
         modelMap.put("award", award);
         modelMap.put("success", Boolean.valueOf(true));
      } else {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "empty awardId");
      }

      return modelMap;
   }

   @RequestMapping(
      value = {"/addaward"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map addAward(HttpServletRequest request) {
      HashMap modelMap = new HashMap();
      if(!CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         ObjectMapper mapper = new ObjectMapper();
         Award award = null;
         String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
         ImageHolder thumbnail = null;
         CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

         try {
            if(multipartResolver.isMultipart(request)) {
               thumbnail = this.handleImage(request, thumbnail);
            }
         } catch (Exception var12) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var12.toString());
            return modelMap;
         }

         try {
            award = (Award)mapper.readValue(awardStr, Award.class);
         } catch (Exception var11) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var11.toString());
            return modelMap;
         }

         if(award != null && thumbnail != null) {
            try {
               Shop e = (Shop)request.getSession().getAttribute("currentShop");
               award.setShopId(e.getShopId());
               AwardExecution ae = this.awardService.addAward(award, thumbnail);
               if(ae.getState() == AwardStateEnum.SUCCESS.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", ae.getStateInfo());
               }
            } catch (RuntimeException var10) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var10.toString());
               return modelMap;
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "请输入奖品信息");
         }

         return modelMap;
      }
   }

   @RequestMapping(
      value = {"/modifyaward"},
      method = {RequestMethod.POST}
   )
   @ResponseBody
   private Map modifyAward(HttpServletRequest request) {
      boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
      HashMap modelMap = new HashMap();
      if(!statusChange && !CodeUtil.checkVerifyCode(request)) {
         modelMap.put("success", Boolean.valueOf(false));
         modelMap.put("errMsg", "输入了错误的验证码");
         return modelMap;
      } else {
         ObjectMapper mapper = new ObjectMapper();
         Award award = null;
         ImageHolder thumbnail = null;
         CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

         try {
            if(multipartResolver.isMultipart(request)) {
               thumbnail = this.handleImage(request, thumbnail);
            }
         } catch (Exception var12) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var12.toString());
            return modelMap;
         }

         try {
            String e = HttpServletRequestUtil.getString(request, "awardStr");
            award = (Award)mapper.readValue(e, Award.class);
         } catch (Exception var11) {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", var11.toString());
            return modelMap;
         }

         if(award != null) {
            try {
               Shop e1 = (Shop)request.getSession().getAttribute("currentShop");
               award.setShopId(e1.getShopId());
               AwardExecution pe = this.awardService.modifyAward(award, thumbnail);
               if(pe.getState() == AwardStateEnum.SUCCESS.getState()) {
                  modelMap.put("success", Boolean.valueOf(true));
               } else {
                  modelMap.put("success", Boolean.valueOf(false));
                  modelMap.put("errMsg", pe.getStateInfo());
               }
            } catch (RuntimeException var10) {
               modelMap.put("success", Boolean.valueOf(false));
               modelMap.put("errMsg", var10.toString());
               return modelMap;
            }
         } else {
            modelMap.put("success", Boolean.valueOf(false));
            modelMap.put("errMsg", "请输入商品信息");
         }

         return modelMap;
      }
   }

   private Award compactAwardCondition4Search(long shopId, String awardName) {
      Award awardCondition = new Award();
      awardCondition.setShopId(Long.valueOf(shopId));
      if(awardName != null) {
         awardCondition.setAwardName(awardName);
      }

      return awardCondition;
   }

   private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail) throws IOException {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
      CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
      if(thumbnailFile != null) {
         thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
      }

      return thumbnail;
   }
}
