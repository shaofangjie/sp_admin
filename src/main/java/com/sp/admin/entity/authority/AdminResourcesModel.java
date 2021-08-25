package com.sp.admin.entity.authority;

import com.sp.admin.entity.BaseModel;

import java.util.List;

public class AdminResourcesModel extends BaseModel {

    private AdminResourcesModel sourcePid;

    private List<AdminRoleModel> adminRoles;

    private int sourceType; //资源类型 0:目录 1:菜单 2:功能

    private String iconfont; //图标

    private String sourceName; //资源名

    private String sourceUrl; //资源路径

    private String sourceFunction; //资源方法

    private boolean enabled; //是否启用

    private int sourceOrder; //排序ID

    private boolean Lock; //是否锁定不允许修改

    public AdminResourcesModel getSourcePid() {
        return sourcePid;
    }

    public void setSourcePid(AdminResourcesModel sourcePid) {
        this.sourcePid = sourcePid;
    }

    public List<AdminRoleModel> getAdminRoles() {
        return adminRoles;
    }

    public void setAdminRoles(List<AdminRoleModel> adminRoles) {
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
