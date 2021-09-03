package com.sp.admin.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sp.admin.annotation.authentication.SpecifiedPermission;
import com.sp.admin.commonutil.consts.RegexpConsts;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.commonutil.response.ServerResponse;
import com.sp.admin.controller.BaseController;
import com.sp.admin.entity.authority.AdminRoleEntity;
import com.sp.admin.forms.authority.RoleAddForm;
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

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/resourceTree")
    @SpecifiedPermission(value = "authority.AdminRoleController.add")
    public JSONObject resourceTree() {

        JSONObject resourceTreeJson = adminRoleService.getResourceTreeJson(null);

        return resourceTreeJson;
    }

    @GetMapping("/add")
    @SpecifiedPermission("authority.AdminRoleController.add")
    public ModelAndView add(ModelAndView modelAndView) {

        modelAndView.setViewName("/authority/adminRoleAdd.btl");

        return modelAndView;
    }

    @PostMapping("/doAdd")
    @SpecifiedPermission(value = "authority.AdminRoleController.add")
    public ServerResponse addHandler(@Valid RoleAddForm roleAddForm, HttpServletResponse response) {

        ResponseCode responseCode = adminRoleService.adminRoleSave(roleAddForm);

        switch (responseCode) {
            case ROLE_ADD_SUCCESS:
                return ServerResponse.createBySuccessMessage(ResponseCode.ROLE_ADD_SUCCESS.getDesc());
            case ROLE_NAME_EXIST:
                return ServerResponse.createByErrorMessage(ResponseCode.ROLE_NAME_EXIST.getDesc());
            case ROLE_RESOURCE_IS_ERROR:
                return ServerResponse.createByErrorMessage(ResponseCode.ROLE_RESOURCE_IS_ERROR.getDesc());
            case ROLE_ADD_FAILED:
                return ServerResponse.createByErrorMessage(ResponseCode.ROLE_ADD_FAILED.getDesc());
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return ServerResponse.createByErrorMessage("添加失败,请重试.");
        }

    }

}
