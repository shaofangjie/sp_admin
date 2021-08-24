package com.sp.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.sp.admin.commonutil.RedisUtil;
import com.sp.admin.commonutil.log.WebLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        logger.info("----------" + request.getSession().getId());
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 38);
        try {
            redisUtil.set("captcha-"+request.getSession().getId(), captcha.getCode(), 300);
            request.getSession().setAttribute("captcha", captcha.getCode());
            response.setContentType("image/png");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            captcha.write(response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("login")
    public ModelAndView loginIndex(HttpSession session, HttpServletResponse response) {
        ModelAndView loginPage = new ModelAndView();
        logger.info("----------" + session.getId());
        if (null != session.getAttribute("adminId")) {
            loginPage.setViewName("redirect:/");
        } else {
            loginPage.setViewName("login.btl");
        }
        return loginPage;
    }

    @GetMapping("/test")
    public ModelAndView testPage() {
        ModelAndView testPage = new ModelAndView();
        testPage.setViewName("login.btl");
        return testPage;
    }


}
