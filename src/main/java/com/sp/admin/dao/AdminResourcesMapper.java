package com.sp.admin.dao;

import com.sp.admin.entity.authority.AdminResourcesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminResourcesMapper {

    List<AdminResourcesEntity> selectAdminResourcesByAdminId(@Param("adminId") long adminId);

    AdminResourcesEntity selectAdminResourceByAdminIdAndFun(@Param("adminId") long adminId, @Param("sourceFunction") String sourceFunction);

    List<AdminResourcesEntity> selectResourceByPid(@Param("sourcePid") long sourcePid);

    List<AdminResourcesEntity> selectAllResources();

    List<AdminResourcesEntity> selectAllEnableResources();

    List<AdminResourcesEntity> selectAdminResourcesByRoleId(@Param("roleId") long roleId);

    List<AdminResourcesEntity> selectResourceByIds(@Param("resourceIds") String resourceIds);

    AdminResourcesEntity selectResourceById(@Param("resourceId") long resourceId);

    long insertResource(AdminResourcesEntity adminResourcesEntity);

    long updateResource(AdminResourcesEntity adminResourcesEntity);

    long deleteResource(AdminResourcesEntity adminResourcesEntity);

    long deleteRoleResource(@Param("resourceId") long resourceId);

    Map<String,Object> selectResourceUseCount(@Param("resourceId") long resourceId);

}
