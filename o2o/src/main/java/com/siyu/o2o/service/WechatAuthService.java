package com.siyu.o2o.service;

import com.siyu.o2o.dto.WechatAuthExecution;
import com.siyu.o2o.entity.WechatAuth;
import com.siyu.o2o.exceptions.WechatAuthOperationException;

public interface WechatAuthService {

   WechatAuth getWechatAuthByOpenId(String var1);

   WechatAuthExecution register(WechatAuth var1) throws WechatAuthOperationException;
}
