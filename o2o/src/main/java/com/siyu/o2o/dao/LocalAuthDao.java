package com.siyu.o2o.dao;

import com.siyu.o2o.entity.LocalAuth;
import java.util.Date;
import org.apache.ibatis.annotations.Param;

public interface LocalAuthDao {

   LocalAuth queryLocalByUserNameAndPwd(@Param("username") String var1, @Param("password") String var2);

   LocalAuth queryLocalByEmailAndPwd(@Param("email") String var1, @Param("password") String var2);

   LocalAuth queryLocalByUserName(@Param("username") String var1);

   LocalAuth queryLocalByEmail(@Param("email") String var1);

   LocalAuth queryLocalByUserId(@Param("userId") long var1);

   int insertLocalAuth(LocalAuth var1);

   int updateLocalAuth(@Param("userId") Long var1, @Param("username") String var2, @Param("password") String var3, @Param("newPassword") String var4, @Param("lastEditTime") Date var5);
}
