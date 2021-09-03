package com.sp.admin.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.digest.DigestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sp.admin.annotation.IsTryAgain;
import com.sp.admin.commonutil.redis.RedisUtil;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.dao.AdminMapper;
import com.sp.admin.dao.AdminRoleMapper;
import com.sp.admin.entity.authority.AdminEntity;
import com.sp.admin.entity.authority.AdminRoleEntity;
import com.sp.admin.forms.LoginForm;
import com.sp.admin.forms.authority.AdminAddForm;
import com.sp.admin.forms.authority.AdminDelForm;
import com.sp.admin.forms.authority.AdminEditForm;
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

        try {
            if (!isDev && !loginForm.getCaptcha().equals(redisUtil.get("captcha-" + request.getSession().getId()))) {
                return ResponseCode.CAPTCHA_FAILED;
            }
            AdminEntity admin = adminMapper.selectAdminInfoByUserName(loginForm.getUserName());
            if (null == admin) {
                return ResponseCode.USERNAME_PWD_FAILED;
            } else if (DigestUtil.sha256Hex(loginForm.getPassword().trim()).equals(admin.getPassword())) {
                request.getSession().setAttribute("isLogin", true);
                request.getSession().setAttribute("adminId", admin.getId());
                redisUtil.set("loginAdmin", admin);
                return ResponseCode.LOGIN_SUCCESS;
            }
            if (!admin.isEnabled()) {
                return ResponseCode.USER_DISABLE;
            }
        } catch (Exception e) {
            return ResponseCode.LOGIN_FAILED;
        }

        return ResponseCode.LOGIN_FAILED;

    }

    public List<AdminRoleEntity> getAllRoleList() {

        return adminRoleMapper.selectAllRoles();

    }

    public PageInfo<AdminEntity> getAdminPageList(AdminSearchForm adminSearchForm, int page, int limit) {

        PageHelper.startPage(page, limit);
        List<AdminEntity> adminEntityList = adminMapper.selectAllAdminInfo(adminSearchForm.getUserName(), adminSearchForm.getNickName(), Long.parseLong(adminSearchForm.getRoleId()));

        return new PageInfo<>(adminEntityList);
    }

    @Transactional
    public ResponseCode adminSave(AdminAddForm adminAddForm) {

        AdminEntity adminEntity = adminMapper.selectAdminInfoByUserName(adminAddForm.getUserName());

        AdminRoleEntity adminRoleEntity = adminRoleMapper.selectRoleById(adminAddForm.getRoleId());

        if (null == adminRoleEntity) {
            return ResponseCode.USER_ROLE_NOT_EXIST;
        }

        if (null != adminEntity) {
            return ResponseCode.USER_EXIST;
        } else {
            adminEntity = new AdminEntity();
            adminEntity.setUserName(adminAddForm.getUserName());
            adminEntity.setNickName(adminAddForm.getNickName());
            adminEntity.setRoleId(Long.parseLong(adminAddForm.getRoleId()));
            adminEntity.setLock(false);
            adminEntity.setEnabled(null != adminAddForm.getEnable() && "1".equals(adminAddForm.getEnable()));
            adminEntity.setPassword(DigestUtil.sha256Hex(adminAddForm.getPassword().trim()));
            adminEntity.setVersion(1L);
            adminEntity.setWhenCreated(DateTime.now().toTimestamp());
            adminEntity.setWhenUpdated(DateTime.now().toTimestamp());

            adminMapper.insertAdmin(adminEntity);

            long adminId = adminEntity.getId();

            if (0 != adminId) {
                return ResponseCode.USER_ADD_SUCCESS;
            } else {
                return ResponseCode.USER_ADD_FAILED;
            }
        }

    }

    public AdminEntity getAdminInfo(Long adminId) {

        return adminMapper.selectAdminInfoById(adminId);

    }

    @Transactional
    @IsTryAgain
    public ResponseCode adminUpdate(AdminEditForm adminEditForm) {

        AdminEntity adminEntity = adminMapper.selectAdminInfoById(Long.parseLong(adminEditForm.getAdminId()));

        if (null == adminEntity) {
            return ResponseCode.USER_NOT_EXIST;
        }

        if (adminEntity.isLock()) {
            return ResponseCode.USER_CANT_EDIT;
        }

        AdminRoleEntity adminRoleEntity = adminRoleMapper.selectRoleById(adminEditForm.getRoleId());

        if (null == adminRoleEntity) {
            return ResponseCode.USER_ROLE_NOT_EXIST;
        }

        adminEntity.setNickName(adminEditForm.getNickName());
        adminEntity.setRoleId(Long.parseLong(adminEditForm.getRoleId()));
        adminEntity.setEnabled(null != adminEditForm.getEnable() && "1".equals(adminEditForm.getEnable()));
        adminEntity.setPassword(DigestUtil.sha256Hex(adminEditForm.getPassword().trim()));
        adminEntity.setWhenUpdated(DateTime.now().toTimestamp());

        Long row = adminMapper.updateAdmin(adminEntity);

        if (row != 0) {
            return ResponseCode.USER_EDIT_SUCCESS;
        } else {
            return ResponseCode.USER_EDIT_FAILED;
        }

    }

    @Transactional
    public ResponseCode adminDelete(AdminDelForm adminDelForm) {

        AdminEntity adminEntity = this.getAdminInfo(Long.parseLong(adminDelForm.getAdminId()));

        if (null == adminEntity) {
            return ResponseCode.USER_NOT_EXIST;
        }

        if (adminEntity.isLock()) {
            return ResponseCode.USER_CANT_DEL;
        }

        Long row = adminMapper.deleteAdmin(adminEntity);

        if (row != 0) {
            return ResponseCode.USER_DEL_SUCCESS;
        } else {
            return ResponseCode.USER_DEL_FAILED;
        }

    }


}
