package com.sp.admin.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sp.admin.annotation.authentication.SpecifiedPermission;
import com.sp.admin.commonutil.consts.RegexpConsts;
import com.sp.admin.controller.BaseController;
import com.sp.admin.entity.authority.AdminEntity;
import com.sp.admin.entity.authority.AdminRoleEntity;
import com.sp.admin.forms.authority.AdminSearchForm;
import com.sp.admin.forms.authority.RoleSearchForm;
import com.sp.admin.results.AdminRoleResult;
import com.sp.admin.service.AdminRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/authority/AdminRole")
public class AdminRoleController extends BaseController{

    @Autowired
    AdminRoleService adminRoleService;

    @GetMapping("/index")
    @SpecifiedPermission("authority.AdminRoleController.index")
    public ModelAndView index(ModelAndView modelAndView) {

        modelAndView.setViewName("/authority/adminRoleIndex.btl");

        return modelAndView;
    }

    @PostMapping("/list")
    @SpecifiedPermission("authority.AdminRoleController.list")
    public JSONObject roleList(@Valid RoleSearchForm roleSearchForm, @Pattern(regexp = RegexpConsts.PAGE, message = "页码格式不合法") String page, @Pattern(regexp = RegexpConsts.LIMIT, message = "分页条数格式不合法") String limit) {

        PageInfo<AdminRoleEntity> adminRoleEntityPageInfo = adminRoleService.getRolePageList(roleSearchForm, Integer.parseInt(page), Integer.parseInt(limit));

        List<Object> adminRoleResultList = new ArrayList<>();

        for (AdminRoleEntity adminRole : adminRoleEntityPageInfo.getList()) {
            AdminRoleResult adminRoleResult = new AdminRoleResult();
            adminRoleResult.setId(adminRole.getId());
            adminRoleResult.setRoleName(adminRole.getRoleName());
            adminRoleResult.setAdminNum(adminRoleService.getRoleUserCountByRoleId(adminRole.getId()).getAdminNum());
            adminRoleResult.setWhenUpdated(adminRole.getWhenUpdated());
            adminRoleResult.setWhenCreated(adminRole.getWhenCreated());
            adminRoleResult.setLock(adminRole.isLock() ? 1 : 0);
            adminRoleResultList.add(adminRoleResult);
        }

        return buildTableResult(0, "", adminRoleEntityPageInfo.getTotal(), adminRoleResultList);

    }

}
