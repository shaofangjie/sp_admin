package com.sp.admin.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.sp.admin.commonutil.RedisUtil;
import com.sp.admin.commonutil.ResponseCode;
import com.sp.admin.dao.AdminMapper;
import com.sp.admin.entity.authority.AdminEntity;
import com.sp.admin.forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class AdminService {

    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    private AdminMapper adminMapper;

    @Transactional
    public ResponseCode getAdminInfoByUserName(LoginForm loginForm, HttpServletRequest request, boolean isDev) {

        try{
            if (!isDev && !loginForm.getCaptcha().equals(redisUtil.get("captcha-" + request.getSession().getId()))) {
                return ResponseCode.CAPTCHA_FAILED;
            }
            AdminEntity admin = adminMapper.selectAdminInfoByUserName(loginForm.getUserName());
            if (null == admin) {
                return ResponseCode.USERNAME_PWD_FAILED;
            } else if(DigestUtil.sha256Hex(loginForm.getPassword().trim()).equals(admin.getPassword())) {
                request.getSession().setAttribute("isLogin", true);
                request.getSession().setAttribute("adminId", admin.getId());
                return ResponseCode.LOGIN_SUCCESS;
            }
            if (!admin.isEnabled()){
                return ResponseCode.USER_DISABLE;
            }
        }catch (Exception e){
            return ResponseCode.LOGIN_FAILED;
        }

        return ResponseCode.LOGIN_FAILED;

    }

}
