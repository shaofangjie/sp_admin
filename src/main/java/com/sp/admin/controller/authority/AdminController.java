package com.sp.admin.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sp.admin.annotation.authentication.SpecifiedPermission;
import com.sp.admin.commonutil.consts.RegexpConsts;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.commonutil.response.ServerResponse;
import com.sp.admin.controller.BaseController;
import com.sp.admin.entity.authority.AdminEntity;
import com.sp.admin.forms.authority.*;
import com.sp.admin.results.AdminResult;
import com.sp.admin.service.AdminService;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

            AdminResult adminResult = BeanCopyUtils.copyBean(admin, AdminResult.class);
            adminResult.setRoleName(admin.getAdminRole().getRoleName());
            adminResult.setEnable(admin.isEnabled() ? 1 : 0);
            adminResult.setLock(admin.isLock() ? 1 : 0);
            adminResult.setWhenUpdated(admin.getWhenUpdated());
            adminResult.setWhenCreated(admin.getWhenCreated());

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
    public ServerResponse addHandler(@Valid AdminAddForm adminAddForm, HttpServletResponse response) {

        ResponseCode responseCode = adminService.adminSave(adminAddForm);

        switch (responseCode) {
            case ADD_SUCCESS:
                return ServerResponse.createBySuccessMessage(ResponseCode.ADD_SUCCESS.getDesc());
            case USER_EXIST:
                return ServerResponse.createByErrorMessage(ResponseCode.USER_EXIST.getDesc());
            case ROLE_NOT_EXIST:
                return ServerResponse.createByErrorMessage(ResponseCode.ROLE_NOT_EXIST.getDesc());
            case ADD_FAILED:
                return ServerResponse.createByErrorMessage(ResponseCode.ADD_FAILED.getDesc());
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return ServerResponse.createByErrorMessage("添加失败,请重试.");
        }

    }

    @GetMapping("/edit")
    @SpecifiedPermission("authority.AdminController.edit")
    public ModelAndView edit(@Valid AdminEditPageForm adminEditPageForm, HttpServletResponse response, ModelAndView modelAndView) {

        AdminEntity adminEntity = adminService.getAdminInfo(Long.parseLong(adminEditPageForm.getAdminId()));

        if (null == adminEntity) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return modelAndView;
        }

        modelAndView.addObject("adminRoleList", adminService.getAllRoleList());
        modelAndView.addObject("adminInfo", adminEntity);
        modelAndView.setViewName("/authority/adminEdit.btl");

        return modelAndView;
    }

    @PostMapping("/doEdit")
    @SpecifiedPermission("authority.AdminController.edit")
    public ServerResponse editHandler(@Valid AdminEditForm adminEditForm, HttpServletResponse response) {

        ResponseCode responseCode = adminService.adminUpdate(adminEditForm);

        switch (responseCode) {
            case EDIT_SUCCESS:
                return ServerResponse.createBySuccessMessage(ResponseCode.EDIT_SUCCESS.getDesc());
            case ADMIN_NOT_EXIST:
                return ServerResponse.createByErrorMessage(ResponseCode.ADMIN_NOT_EXIST.getDesc());
            case EDIT_FAILED:
                return ServerResponse.createByErrorMessage(ResponseCode.EDIT_FAILED.getDesc());
            case ROLE_NOT_EXIST:
                return ServerResponse.createByErrorMessage(ResponseCode.ROLE_NOT_EXIST.getDesc());
            case CANT_EDIT:
                return ServerResponse.createByErrorMessage(ResponseCode.CANT_EDIT.getDesc());
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return ServerResponse.createByErrorMessage("修改失败,请重试.");
        }

    }

    @GetMapping("/del")
    @SpecifiedPermission("authority.AdminController.del")
    public ServerResponse del(@Valid AdminDelForm adminDelForm, HttpServletResponse response) {

        ResponseCode responseCode = adminService.adminDelete(adminDelForm);

        switch (responseCode) {
            case DEL_SUCCESS:
                return ServerResponse.createBySuccessMessage(ResponseCode.DEL_SUCCESS.getDesc());
            case ADMIN_NOT_EXIST:
                return ServerResponse.createByErrorMessage(ResponseCode.ADMIN_NOT_EXIST.getDesc());
            case DEL_FAILED:
                return ServerResponse.createByErrorMessage(ResponseCode.DEL_FAILED.getDesc());
            case CANT_DEL:
                return ServerResponse.createByErrorMessage(ResponseCode.CANT_DEL.getDesc());
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return ServerResponse.createByErrorMessage("修改失败,请重试.");
        }

    }

}
