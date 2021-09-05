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

public class ResourceDelForm {

    @NotNull(message = "资源ID不能为空")
    @Pattern(regexp = RegexpConsts.NUM, message = "资源ID格式不合法")
    private String resourceId;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

}
