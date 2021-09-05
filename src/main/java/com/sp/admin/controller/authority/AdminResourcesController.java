package com.sp.admin.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.sp.admin.annotation.authentication.SpecifiedPermission;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.commonutil.response.ServerResponse;
import com.sp.admin.controller.BaseController;
import com.sp.admin.entity.authority.AdminResourcesEntity;
import com.sp.admin.forms.authority.*;
import com.sp.admin.results.AdminResourceResult;
import com.sp.admin.service.AdminResourcesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/authority/AdminResources")
public class AdminResourcesController extends BaseController {

    @Autowired
    AdminResourcesService adminResourcesService;

    @GetMapping("/index")
    @SpecifiedPermission("authority.AdminResourcesController.index")
    public ModelAndView index(ModelAndView modelAndView) {

        modelAndView.setViewName("/authority/adminResourcesIndex.btl");

        return modelAndView;
    }

    @GetMapping("/list")
    @SpecifiedPermission("authority.AdminResourcesController.list")
    public JSONObject resourceList() {

        List<AdminResourcesEntity> adminResourceList = adminResourcesService.getAdminResourceList();

        List<Object> adminResourceResultList = new ArrayList<>();
        for (AdminResourcesEntity adminResources : adminResourceList) {
            AdminResourceResult adminResourceResult = new AdminResourceResult();
            adminResourceResult.setId(adminResources.getId());
            adminResourceResult.setPid(null == adminResources.getSourcePid() ? 0 : adminResources.getSourcePid());
            adminResourceResult.setSourceType(adminResources.getSourceType());
            adminResourceResult.setSourceName(adminResources.getSourceName());
            adminResourceResult.setSourceFunction(adminResources.getSourceFunction());
            adminResourceResult.setSourceOrder(adminResources.getSourceOrder());
            adminResourceResult.setEnabled(adminResources.isEnabled() ? 1 : 0);
            adminResourceResult.setLock(adminResources.isLock() ? 1 : 0);
            adminResourceResultList.add(adminResourceResult);
        }

        return buildTableResult(0, "", adminResourceResultList.size(), adminResourceResultList);

    }

    @GetMapping("/add")
    @SpecifiedPermission("authority.AdminResourcesController.add")
    public ModelAndView add(ModelAndView modelAndView) {

        modelAndView.addObject("allParentResources", adminResourcesService.getAllParentResources());
        modelAndView.setViewName("/authority/adminResourceAdd.btl");

        return modelAndView;
    }

    @PostMapping("/doAdd")
    @SpecifiedPermission("authority.AdminResourcesController.add")
    public ServerResponse addHandler(ResourceAddForm resourceAddForm, HttpServletResponse response) {

        ResponseCode responseCode = adminResourcesService.adminResourceSave(resourceAddForm);

        switch (responseCode) {
            case RESOURCE_ADD_SUCCESS:
                return ServerResponse.createBySuccessMessage(ResponseCode.RESOURCE_ADD_SUCCESS.getDesc());
            case RESOURCE_PARENT_IS_NULL:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_PARENT_IS_NULL.getDesc());
            case RESOURCE_ADD_FAILED:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_ADD_FAILED.getDesc());
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return ServerResponse.createByErrorMessage("添加失败,请重试.");
        }

    }

    @GetMapping("/edit")
    @SpecifiedPermission("authority.AdminResourcesController.edit")
    public ModelAndView edit(@Valid ResourceEditPageForm resourceEditPageForm, ModelAndView modelAndView) {

        AdminResourcesEntity adminResourcesEntity = adminResourcesService.getResourceById(Long.parseLong(resourceEditPageForm.getResourceId()));

        if (null == adminResourcesEntity) {
            return null;
        }

        modelAndView.addObject("adminResource", adminResourcesEntity);
        modelAndView.addObject("allParentResources", adminResourcesService.getAllParentResources());

        modelAndView.setViewName("/authority/adminResourceEdit.btl");

        return modelAndView;

    }

    @PostMapping("/doEdit")
    @SpecifiedPermission("authority.AdminResourcesController.edit")
    public ServerResponse editHandler(@Valid ResourceEditForm resourceEditForm, HttpServletResponse response) {

        ResponseCode responseCode = adminResourcesService.adminResourceUpdate(resourceEditForm);

        switch (responseCode) {
            case RESOURCE_EDIT_SUCCESS:
                return ServerResponse.createBySuccessMessage(ResponseCode.RESOURCE_EDIT_SUCCESS.getDesc());
            case RESOURCE_PARENT_IS_NULL:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_PARENT_IS_NULL.getDesc());
            case RESOURCE_CANT_EDIT:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_CANT_EDIT.getDesc());
            case RESOURCE_ADD_FAILED:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_ADD_FAILED.getDesc());
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return ServerResponse.createByErrorMessage("修改失败,请重试.");
        }

    }

    @GetMapping("/del")
    @SpecifiedPermission("authority.AdminResourcesController.del")
    public ServerResponse del(@Valid ResourceDelForm resourceDelForm, HttpServletResponse response) {

        ResponseCode responseCode = adminResourcesService.adminResourceDelete(resourceDelForm);

        switch (responseCode) {
            case RESOURCE_DEL_SUCCESS:
                return ServerResponse.createBySuccessMessage(ResponseCode.RESOURCE_DEL_SUCCESS.getDesc());
            case RESOURCE_HAS_CHILD:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_HAS_CHILD.getDesc());
            case RESOURCE_CANT_DEL:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_CANT_DEL.getDesc());
            case RESOURCE_IS_NULL:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_IS_NULL.getDesc());
            case RESOURCE_DEL_FAILED:
                return ServerResponse.createByErrorMessage(ResponseCode.RESOURCE_DEL_FAILED.getDesc());
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return ServerResponse.createByErrorMessage("添加失败,请重试.");
        }

    }

}
