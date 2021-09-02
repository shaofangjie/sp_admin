package com.sp.admin.dao;

import com.sp.admin.entity.authority.AdminRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminRoleMapper {

    List<AdminRoleEntity> selectAllRoles();

    List<AdminRoleEntity> selectRolesByName(@Param("roleName") String roleName);

    AdminRoleEntity selectRoleById(@Param("roleId") String roleId);

    AdminRoleEntity selectRoleUserCountByRoleId(@Param("roleId") Long roleId);

}
