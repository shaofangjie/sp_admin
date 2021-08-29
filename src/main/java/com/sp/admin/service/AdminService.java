package com.sp.admin.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sp.admin.commonutil.redis.RedisUtil;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.dao.AdminMapper;
import com.sp.admin.dao.AdminRoleMapper;
import com.sp.admin.entity.authority.AdminEntity;
import com.sp.admin.entity.authority.AdminRoleEntity;
import com.sp.admin.forms.LoginForm;
import com.sp.admin.forms.authority.AdminSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Transactional
    public ResponseCode AdminLogin(LoginForm loginForm, HttpServletRequest request, boolean isDev) {

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
                redisUtil.set("loginAdmin", admin);
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

    public List<AdminRoleEntity> getAllRoleList() {

        return adminRoleMapper.selectAllRoles();

    }

    public PageInfo<AdminEntity> getAdminPageList(AdminSearchForm adminSearchForm, int page, int limit) {

        PageHelper.startPage(page,limit);
        List<AdminEntity> adminEntityList = adminMapper.selectAllAdminInfo(adminSearchForm.getUserName(), adminSearchForm.getNickName(), Long.parseLong(adminSearchForm.getRoleId()));

        return new PageInfo<>(adminEntityList);
    }

}
