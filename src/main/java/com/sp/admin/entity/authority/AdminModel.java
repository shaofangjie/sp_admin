package com.sp.admin.entity.authority;

import com.sp.admin.entity.BaseModel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: shaofangjie
 * Date: 2018-10-26
 * Time: 2:47 AM
 */

public class AdminModel extends BaseModel {

    private String userName;

    private String password;

    private String nickName;

    private AdminRoleModel adminRole;

    private boolean enabled;

    private boolean Lock; //是否锁定不允许修改

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public AdminRoleModel getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(AdminRoleModel adminRole) {
        this.adminRole = adminRole;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLock() {
        return Lock;
    }

    public void setLock(boolean lock) {
        Lock = lock;
    }
}
