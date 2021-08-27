package com.sp.admin.entity.authority;

import com.sp.admin.entity.BaseEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: shaofangjie
 * Date: 2018-10-26
 * Time: 2:35 AM
 */

public class AdminRoleEntity extends BaseEntity {

    private String roleName;

    private List<AdminEntity> admin;

    private List<AdminResourcesEntity> adminRoleResources;

    private boolean Lock ; //是否锁定不允许修改,此值只有超级管理员组为1

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<AdminEntity> getAdmin() {
        return admin;
    }

    public void setAdmin(List<AdminEntity> admin) {
        this.admin = admin;
    }

    public List<AdminResourcesEntity> getAdminRoleResources() {
        return adminRoleResources;
    }

    public void setAdminRoleResources(List<AdminResourcesEntity> adminRoleResources) {
        this.adminRoleResources = adminRoleResources;
    }

    public boolean isLock() {
        return Lock;
    }

    public void setLock(boolean lock) {
        Lock = lock;
    }
}
