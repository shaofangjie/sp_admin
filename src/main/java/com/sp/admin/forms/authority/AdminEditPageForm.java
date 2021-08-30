package com.sp.admin.forms.authority;

import com.sp.admin.annotation.validation.EmptyOrPattern;
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

public class AdminEditPageForm {

    @NotNull(message = "管理员ID不能为空")
    @Pattern(regexp = RegexpConsts.ID, message = "管理员ID格式不合法")
    private String adminId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

}
