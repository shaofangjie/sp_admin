package com.sp.admin.dao;

import com.sp.admin.entity.authority.AdminResourcesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminResourcesMapper {

    List<AdminResourcesEntity> selectAdminResourcesByAdminId(@Param("adminId") int adminId);

}
