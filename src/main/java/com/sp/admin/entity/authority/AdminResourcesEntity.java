package com.sp.admin.entity.authority;

import com.sp.admin.entity.BaseEntity;

import java.util.List;

public class AdminResourcesEntity extends BaseEntity {

    private Long sourcePid;

    private List<AdminResourcesEntity> childResources;

    private List<AdminRoleEntity> adminRoles;

    private int sourceType; //资源类型 0:目录 1:菜单 2:功能

    private String iconfont; //图标

    private String sourceName; //资源名

    private String sourceUrl; //资源路径

    private String sourceFunction; //资源方法

    private boolean enabled; //是否启用

    private int sourceOrder; //排序ID

    private boolean Lock; //是否锁定不允许修改

    public Long getSourcePid() {
        return sourcePid;
    }

    public List<AdminResourcesEntity> getChildResources() {
        return childResources;
    }

    public void setChildResources(List<AdminResourcesEntity> childResources) {
        this.childResources = childResources;
    }

    public void setSourcePid(Long sourcePid) {
        this.sourcePid = sourcePid;
    }

    public List<AdminRoleEntity> getAdminRoles() {
        return adminRoles;
    }

    public void setAdminRoles(List<AdminRoleEntity> adminRoles) {
        this.adminRoles = adminRoles;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getIconfont() {
        return iconfont;
    }

    public void setIconfont(String iconfont) {
        this.iconfont = iconfont;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceFunction() {
        return sourceFunction;
    }

    public void setSourceFunction(String sourceFunction) {
        this.sourceFunction = sourceFunction;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSourceOrder() {
        return sourceOrder;
    }

    public void setSourceOrder(int sourceOrder) {
        this.sourceOrder = sourceOrder;
    }

    public boolean isLock() {
        return Lock;
    }

    public void setLock(boolean lock) {
        Lock = lock;
    }
}
