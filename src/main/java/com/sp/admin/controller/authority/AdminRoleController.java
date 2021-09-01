package com.sp.admin.controller.authority;

import com.sp.admin.annotation.authentication.SpecifiedPermission;
import com.sp.admin.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/authority/AdminRole")
public class AdminRoleController extends BaseController{

    @GetMapping("/index")
    @SpecifiedPermission("authority.AdminRoleController.index")
    public ModelAndView index(ModelAndView modelAndView) {

        modelAndView.setViewName("/authority/adminRoleIndex.btl");

        return modelAndView;
    }

}
