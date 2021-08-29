package com.sp.admin.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sp.admin.annotation.authentication.SpecifiedPermission;
import com.sp.admin.commonutil.RegexpConsts;
import com.sp.admin.controller.BaseController;
import com.sp.admin.entity.authority.AdminEntity;
import com.sp.admin.forms.authority.AdminSearchForm;
import com.sp.admin.results.AdminResult;
import com.sp.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/authority/Admin")
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;

    @GetMapping("/index")
    @SpecifiedPermission("authority.AdminController.index")
    public ModelAndView index(ModelAndView model) {

        model.addObject("adminRoleList", adminService.getAllRoleList());
        model.setViewName("/authority/adminIndex.btl");

        return model;
    }

    @PostMapping("/list")
    @SpecifiedPermission("authority.AdminController.list")
    public JSONObject adminList(@Valid AdminSearchForm adminSearchForm, @Pattern(regexp = RegexpConsts.PAGE, message = "页码格式不合法") String page, @Pattern(regexp = RegexpConsts.LIMIT, message = "分页条数格式不合法") String limit) {

        PageInfo<AdminEntity> adminEntityPageInfo = adminService.getAdminPageList(adminSearchForm, Integer.parseInt(page), Integer.parseInt(limit));

        List<Object> adminResultList = new ArrayList<>();
        for (AdminEntity admin : adminEntityPageInfo.getList()) {
            AdminResult adminResult = new AdminResult();
            adminResult.setId(admin.getId());
            adminResult.setUserName(admin.getUserName());
            adminResult.setNickName(admin.getNickName());
            adminResult.setRoleName(admin.getAdminRole().getRoleName());
            adminResult.setWhenUpdated(admin.getWhenUpdated());
            adminResult.setWhenCreated(admin.getWhenCreated());
            adminResult.setEnable(admin.isEnabled() ? 1 : 0);
            adminResult.setLock(admin.isLock() ? 1 : 0);
            adminResultList.add(adminResult);
        }

        return buildTableResult(0, "", adminEntityPageInfo.getTotal(), adminResultList);

    }

}
