package com.sp.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.sp.admin.annotation.authentication.IgnorePermissionCheck;
import com.sp.admin.service.AdminResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MainController extends BaseController{

    @Autowired
    private AdminResourcesService adminResourcesService;

    @IgnorePermissionCheck()
    @RequestMapping(value={"/","/index"}, method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, ModelAndView mainPage) {

        JSONObject menuList = adminResourcesService.getMenuJsonByAdminId(Long.parseLong(request.getSession().getAttribute("adminId").toString()));

        mainPage.addObject("menuList", menuList);
        mainPage.setViewName("main.btl");

        return mainPage;
    }

    @IgnorePermissionCheck()
    @GetMapping("/dashboard")
    public ModelAndView dashBoard(ModelAndView modelAndView) {
        modelAndView.setViewName("dashboard.btl");
        return modelAndView;
    }

}
