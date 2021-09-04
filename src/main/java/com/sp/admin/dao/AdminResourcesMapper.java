package com.sp.admin.dao;

import com.sp.admin.entity.authority.AdminResourcesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminResourcesMapper {

    List<AdminResourcesEntity> selectAdminResourcesByAdminId(@Param("adminId") long adminId);

    AdminResourcesEntity selectAdminResourceByAdminIdAndFun(@Param("adminId") long adminId, @Param("sourceFunction") String sourceFunction);

    List<AdminResourcesEntity> selectResourceByPid(@Param("sourcePid") long sourcePid);

    List<AdminResourcesEntity> selectAllResources();

    List<AdminResourcesEntity> selectAllEnableResources();

    List<AdminResourcesEntity> selectAdminResourcesByRoleId(@Param("roleId") long roleId);

    List<AdminResourcesEntity> selectResourceByIds(@Param("resourceIds") String resourceIds);

    AdminResourcesEntity selectResourceById(@Param("resourceIds") long resourceId);

    long insertResource(AdminResourcesEntity adminResourcesEntity);

}
