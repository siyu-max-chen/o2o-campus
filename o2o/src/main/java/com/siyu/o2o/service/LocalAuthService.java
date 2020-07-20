package com.siyu.o2o.service;

import com.siyu.o2o.entity.LocalAuth;
import com.siyu.o2o.exceptions.LocalAuthOperationException;

public interface LocalAuthService {

   LocalAuth getLocalAuthByUsernameAndPwd(String var1, String var2);

   LocalAuth getLocalAuthByEmailAndPwd(String var1, String var2);

   LocalAuth getLocalAuthByUserId(long var1);

   LocalAuthExecution bindLocalAuth(LocalAuth var1) throws LocalAuthOperationException;

   LocalAuthExecution modifyLocalAuth(Long var1, String var2, String var3, String var4) throws LocalAuthOperationException;

   LocalAuthExecution registerLocalAuth(String var1, String var2, String var3) throws LocalAuthOperationException;
}
