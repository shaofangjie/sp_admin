package com.sp.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSONObject;
import com.sp.admin.commonutil.ServerResponse;
import com.sp.admin.forms.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class LoginController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

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
    public ModelAndView loginIndex(HttpSession session) {
        ModelAndView loginPage = new ModelAndView();
        logger.info("----------" + session.getId());
        loginPage.setViewName("login.btl");
        return loginPage;
    }

    @PostMapping("/doLogin")
    public ServerResponse loginHandler(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            response.setStatus(400);
            JSONObject errors = getViolationErrMsg(bindingResult.getFieldErrors());
            return ServerResponse.createByErrorMessage("参数错误", 1, errors);
        }

        //TODO 实现登录逻辑
        return ServerResponse.createBySuccess();

    }

    @GetMapping("/test")
    public ModelAndView testPage() {
        ModelAndView testPage = new ModelAndView();
        testPage.setViewName("login.btl");
        return testPage;
    }


}
