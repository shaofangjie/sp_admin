package com.sp.admin.dao;

import com.sp.admin.entity.authority.AdminEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {

    AdminEntity selectAdminInfoByUserName(@Param("userName") String userName);

}
