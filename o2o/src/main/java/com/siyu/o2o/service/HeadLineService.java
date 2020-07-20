package com.siyu.o2o.service;

import com.siyu.o2o.entity.HeadLine;
import java.util.List;

public interface HeadLineService {

   String HLLISTKEY = "headlinelist";


   List getHeadLineList(HeadLine var1);

   List getBestShopList();
}
