package com.siyu.o2o.dao;

import com.siyu.o2o.entity.WechatAuth;

public interface WechatAuthDao {

   WechatAuth queryWechatInfoByOpenId(String var1);

   int insertWechatAuth(WechatAuth var1);
}
