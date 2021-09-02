package com.sp.admin.forms.authority;


import com.sp.admin.annotation.validation.EmptyOrPattern;
import com.sp.admin.commonutil.consts.RegexpConsts;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: shaofangjie
 * Date: 2018-10-31
 * Time: 10:09 PM
 */

public class RoleSearchForm {

    @EmptyOrPattern(regexp = RegexpConsts.ROLENAME, message = "角色名格式不合法")
    private String roleName;
    @EmptyOrPattern(regexp = RegexpConsts.ORDERCOLUMN, message = "排序字段不合法")
    private String orderColumn;
    @EmptyOrPattern(regexp = RegexpConsts.ORDERDIR, message = "排序方式不合法")
    private String orderDir;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderDir() {
        return orderDir;
    }

    public void setOrderDir(String orderDir) {
        this.orderDir = orderDir;
    }
}
