package com.sp.admin.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sp.admin.annotation.authentication.SpecifiedPermission;
import com.sp.admin.commonutil.consts.RegexpConsts;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.commonutil.response.ServerResponse;
import com.sp.admin.controller.BaseController;
import com.sp.admin.entity.authority.AdminEntity;
import com.sp.admin.forms.authority.AdminAddForm;
import com.sp.admin.forms.authority.AdminEditPageForm;
import com.sp.admin.forms.authority.AdminSearchForm;
import com.sp.admin.results.AdminResult;
import com.sp.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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
    public ModelAndView index(ModelAndView modelAndView) {

        modelAndView.addObject("adminRoleList", adminService.getAllRoleList());
        modelAndView.setViewName("/authority/adminIndex.btl");

        return modelAndView;
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

    @GetMapping("/add")
    @SpecifiedPermission("authority.AdminController.add")
    public ModelAndView add(ModelAndView modelAndView) {

        modelAndView.addObject("adminRoleList", adminService.getAllRoleList());
        modelAndView.setViewName("/authority/adminAdd.btl");

        return modelAndView;
    }

    @PostMapping("/doAdd")
    @SpecifiedPermission("authority.AdminController.add")
    public ServerResponse addHandler(@Valid AdminAddForm adminAddForm, BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            response.setStatus(400);
            JSONObject errors = getViolationErrMsg(bindingResult.getFieldErrors());
            return ServerResponse.createByErrorMessage("参数错误", 1, errors);
        }

        ResponseCode responseCode = adminService.adminSave(adminAddForm);

        switch (responseCode) {
            case ADD_SUCCESS:
                return ServerResponse.createBySuccessMessage("添加成功");
            case USER_EXIST:
                return ServerResponse.createByErrorMessage("用户已存在");
            case ROLE_NOT_EXIST:
                return ServerResponse.createByErrorMessage("角色不存在");
            case ADD_FAILED:
                return ServerResponse.createByErrorMessage("添加失败,请重试.");
            default:
                response.setStatus(400);
                return ServerResponse.createByErrorMessage("添加失败,请重试.");
        }

    }

    @GetMapping("/edit")
    @SpecifiedPermission("authority.AdminController.edit")
    public ModelAndView edit(@Valid AdminEditPageForm adminEditPageForm, BindingResult bindingResult, HttpServletResponse response, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            response.setStatus(400);
            return modelAndView;
        }

        AdminEntity adminEntity = adminService.getAdminInfo(Long.parseLong(adminEditPageForm.getAdminId()));

        if (null == adminEntity) {
            response.setStatus(404);
            return modelAndView;
        }

        modelAndView.addObject("adminRoleList", adminService.getAllRoleList());
        modelAndView.addObject("adminInfo", adminEntity);
        modelAndView.setViewName("/authority/adminEdit.btl");

        return modelAndView;
    }

}
