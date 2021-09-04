package com.sp.admin.forms.authority;

import com.sp.admin.commonutil.consts.RegexpConsts;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: shaofangjie
 * Date: 2018-11-05
 * Time: 8:47 PM
 */

public class RoleDelForm {

    @NotNull(message = "角色ID不能为空")
    @Pattern(regexp = RegexpConsts.ID, message = "角色ID格式不合法")
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
