package com.sp.admin.dao;

import com.sp.admin.entity.authority.AdminEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    AdminEntity selectAdminInfoByUserName(@Param("userName") String userName);

    AdminEntity selectAdminInfoById(@Param("adminId") long adminId);

    AdminEntity selectSuperAdminInfo();

    List<AdminEntity> selectAllAdminInfo(@Param("userName") String userName
            , @Param("nickName") String nickName
            , @Param("roleId") long roleId
    );

    long insertAdmin(AdminEntity admin);

    long updateAdmin(AdminEntity admin);

    long deleteAdmin(AdminEntity admin);

}
