package com.sp.admin.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sp.admin.dao.AdminRoleMapper;
import com.sp.admin.entity.authority.AdminRoleEntity;
import com.sp.admin.forms.authority.RoleSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    public PageInfo<AdminRoleEntity> getRolePageList(RoleSearchForm roleSearchForm, int page, int limit) {

        PageHelper.startPage(page, limit);
        List<AdminRoleEntity> adminRoleEntities = adminRoleMapper.selectRolesByName(roleSearchForm.getRoleName());

        return new PageInfo<>(adminRoleEntities);
    }

    public AdminRoleEntity getRoleUserCountByRoleId(Long roleId) {

        return adminRoleMapper.selectRoleUserCountByRoleId(roleId);

    }



}
