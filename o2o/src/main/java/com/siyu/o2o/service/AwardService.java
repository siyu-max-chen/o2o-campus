package com.siyu.o2o.service;

import com.siyu.o2o.dto.AwardExecution;
import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.entity.Award;

public interface AwardService {

   AwardExecution getAwardList(Award var1, int var2, int var3);

   Award getAwardById(long var1);

   AwardExecution addAward(Award var1, ImageHolder var2);

   AwardExecution modifyAward(Award var1, ImageHolder var2);
}
