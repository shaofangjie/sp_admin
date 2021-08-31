package com.sp.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSONObject;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.commonutil.response.ServerResponse;
import com.sp.admin.commonutil.log.WebLog;
import com.sp.admin.forms.LoginForm;
import com.sp.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
public class LoginController extends BaseController {

    @Autowired
    AdminService adminService;

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 38);
        try {
            redisUtil.set("captcha-" + request.getSession().getId(), captcha.getCode(), 300);
            response.setContentType("image/png");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            captcha.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/login")
    public ModelAndView loginIndex(HttpSession session, ModelAndView modelAndView) {
        if (null != session.getAttribute("isLogin")) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("login.btl");
        }
        return modelAndView;
    }

    @WebLog()
    @PostMapping("/doLogin")
    public ServerResponse loginHandler(@Valid LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {
        log.debug("{}------doLogin", request.getSession().getId());

        ResponseCode responseCode = adminService.AdminLogin(loginForm, request, this.isDev());

        switch (responseCode) {
            case LOGIN_SUCCESS:
                return ServerResponse.createBySuccessMessage("登录成功");
            case CAPTCHA_FAILED:
                return ServerResponse.createByErrorMessage("验证码错误");
            case USER_DISABLE:
                return ServerResponse.createByErrorMessage("账号已禁用");
            case LOGIN_FAILED:
                return ServerResponse.createByErrorMessage("登录失败,请重试.");
            case USERNAME_PWD_FAILED:
                return ServerResponse.createByErrorMessage("用户名或密码错误");
            default:
                response.setStatus(400);
                return ServerResponse.createByErrorMessage("登录失败,请重试.");
        }

    }

}
