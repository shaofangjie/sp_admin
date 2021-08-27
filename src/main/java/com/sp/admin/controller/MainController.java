package com.sp.admin.controller;

import com.sp.admin.annotation.authentication.IgnorePermissionCheck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MainController extends BaseController{

    @IgnorePermissionCheck()
    @GetMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) {

        return "ok";

    }

}
