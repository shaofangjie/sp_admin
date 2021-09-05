package com.sp.admin.service;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.dao.AdminResourcesMapper;
import com.sp.admin.dao.AdminRoleMapper;
import com.sp.admin.entity.authority.AdminResourcesEntity;
import com.sp.admin.entity.authority.AdminRoleEntity;
import com.sp.admin.forms.authority.RoleAddForm;
import com.sp.admin.forms.authority.RoleDelForm;
import com.sp.admin.forms.authority.RoleEditForm;
import com.sp.admin.forms.authority.RoleSearchForm;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Log4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminResourcesMapper adminResourcesMapper;

    public PageInfo<AdminRoleEntity> getRolePageList(RoleSearchForm roleSearchForm, int page, int limit) {

        PageHelper.startPage(page, limit);
        List<AdminRoleEntity> adminRoleEntities = adminRoleMapper.selectRolesByName(roleSearchForm.getRoleName());

        return new PageInfo<>(adminRoleEntities);
    }

    public AdminRoleEntity getRoleUserCountByRoleId(Long roleId) {

        return adminRoleMapper.selectRoleUserCountByRoleId(roleId);

    }

    public AdminRoleEntity getRoleById(Long roleId) {
        return adminRoleMapper.selectRoleById(roleId);
    }

    public JSONObject getResourceTreeJson(Long roleId) {

        List<AdminResourcesEntity> allResourcesList = adminResourcesMapper.selectAllEnableResources();

        List<AdminResourcesEntity> hasResourcesList = new ArrayList<>();

        if (null != roleId && 0 != roleId) {
            hasResourcesList = adminResourcesMapper.selectAdminResourcesByRoleId(roleId);
        }

        JSONArray resourcesTreeArr = new JSONArray();
        for (AdminResourcesEntity topResource : allResourcesList) {
            if (null == topResource.getSourcePid()) {
                JSONObject topMap = new JSONObject();
                topMap.put("value", topResource.getId().toString());
                topMap.put("name", topResource.getSourceName());
                topMap.put("order", topResource.getSourceOrder());
                for (AdminResourcesEntity adminResourcesEntity : hasResourcesList) {
                    if (adminResourcesEntity.getId().equals(topResource.getId())) {
                        topMap.put("checked", true);
                        break;
                    }
                }
                JSONArray topList = new JSONArray();
                for (AdminResourcesEntity secondResource : allResourcesList) {
                    if (secondResource.isEnabled() && null != secondResource.getSourcePid() && secondResource.getSourcePid().equals(topResource.getId())) {
                        JSONObject secondMap = new JSONObject();
                        secondMap.put("value", secondResource.getId().toString());
                        secondMap.put("name", secondResource.getSourceName());
                        secondMap.put("order", secondResource.getSourceOrder());
                        for (AdminResourcesEntity adminResourcesEntity : hasResourcesList) {
                            if (adminResourcesEntity.getId().equals(secondResource.getId())) {
                                secondMap.put("checked", true);
                                break;
                            }
                        }
                        JSONArray secondList = new JSONArray();
                        for (AdminResourcesEntity threeResource : allResourcesList) {
                            if (threeResource.isEnabled() && null != threeResource.getSourcePid() && threeResource.getSourcePid().equals(secondResource.getId())) {
                                JSONObject threeMap = new JSONObject();
                                threeMap.put("value", threeResource.getId().toString());
                                threeMap.put("name", threeResource.getSourceName());
                                threeMap.put("order", threeResource.getSourceOrder());
                                for (AdminResourcesEntity adminResourcesEntity : hasResourcesList) {
                                    if (adminResourcesEntity.getId().equals(threeResource.getId())) {
                                        threeMap.put("checked", true);
                                        break;
                                    }
                                }
                                JSONArray threeList = new JSONArray();
                                for (AdminResourcesEntity fourResource : allResourcesList) {
                                    if (fourResource.isEnabled() && null != fourResource.getSourcePid() && fourResource.getSourcePid().equals(threeResource.getId())) {
                                        JSONObject fourMap = new JSONObject();
                                        fourMap.put("value", fourResource.getId().toString());
                                        fourMap.put("name", fourResource.getSourceName());
                                        fourMap.put("order", fourResource.getSourceOrder());
                                        fourMap.put("checked", hasResourcesList.contains(fourResource));
                                        threeList.add(fourMap);
                                    }
                                }
                                if (threeList.size() > 0) {
                                    threeList.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger("order")));
                                    threeMap.put("list", threeList);
                                }
                                secondList.add(threeMap);
                            }
                        }
                        if (secondList.size() > 0) {
                            secondList.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger("order")));
                            secondMap.put("list", secondList);
                        }
                        topList.add(secondMap);
                    }
                    if (topList.size() > 0) {
                        topList.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger("order")));
                        topMap.put("list", topList);
                    }
                }
                resourcesTreeArr.add(topMap);
            }
        }

        JSONObject resourcesTree = new JSONObject();

        resourcesTree.put("code", 0);
        resourcesTree.put("msg", "ok");
        resourcesTreeArr.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger("order")));
        resourcesTree.put("auth", resourcesTreeArr);

        return resourcesTree;
    }

    public ResponseCode adminRoleSave(RoleAddForm roleAddForm) {

        try{
            AdminRoleEntity adminRoleEntity = adminRoleMapper.selectRoleByRoleName(roleAddForm.getRoleName());

            if (null != adminRoleEntity) {
                return ResponseCode.ROLE_NAME_EXIST;
            }

            String[] resources = roleAddForm.getAuthStr().split(",");

            List<AdminResourcesEntity> adminResourcesEntities = adminResourcesMapper.selectResourceByIds(roleAddForm.getAuthStr());

            if (resources.length != adminResourcesEntities.size() || 0 == resources.length) {
                return ResponseCode.ROLE_RESOURCE_IS_ERROR;
            }

            adminRoleEntity = new AdminRoleEntity();
            adminRoleEntity.setRoleName(roleAddForm.getRoleName());
            adminRoleEntity.setLock(false);
            adminRoleEntity.setVersion(1L);
            adminRoleEntity.setWhenCreated(DateTime.now().toTimestamp());
            adminRoleEntity.setWhenUpdated(DateTime.now().toTimestamp());

            adminRoleMapper.insertRole(adminRoleEntity);

            long roleId = adminRoleEntity.getId();

            for (AdminResourcesEntity adminResourcesEntity : adminResourcesEntities) {
                adminRoleMapper.insertRoleResource(roleId, adminResourcesEntity.getId());
            }

            return ResponseCode.ROLE_ADD_SUCCESS;
        }catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseCode.ROLE_ADD_FAILED;
        }

    }

    public ResponseCode adminRoleUpdate(RoleEditForm roleEditForm) {

        try {
            AdminRoleEntity adminRoleEntity = adminRoleMapper.selectRoleById(Long.parseLong(roleEditForm.getRoleId()));

            if (null == adminRoleEntity) {
                return ResponseCode.ROLE_NOT_EXIST;
            }

            if (adminRoleEntity.isLock()) {
                return ResponseCode.ROLE_CANT_EDIT;
            }

            adminRoleEntity.setRoleName(roleEditForm.getRoleName());
            adminRoleEntity.setWhenUpdated(DateTime.now().toTimestamp());

            long delResources = adminRoleMapper.deleteRoleAllResource(adminRoleEntity.getId());
            log.debug(delResources);

            String[] resources = roleEditForm.getAuthStr().split(",");
            List<AdminResourcesEntity> adminResourcesEntities = adminResourcesMapper.selectResourceByIds(roleEditForm.getAuthStr());
            if (resources.length != adminResourcesEntities.size() || 0 == resources.length) {
                return ResponseCode.ROLE_RESOURCE_IS_ERROR;
            }
            for (AdminResourcesEntity adminResourcesEntity : adminResourcesEntities) {
                adminRoleMapper.insertRoleResource(adminRoleEntity.getId(), adminResourcesEntity.getId());
            }

            long updateRow = adminRoleMapper.updateRole(adminRoleEntity);

            if (updateRow != 0 && delResources != 0) {
                return ResponseCode.ROLE_EDIT_SUCCESS;
            } else {
                return ResponseCode.ROLE_EDIT_FAILED;
            }

        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseCode.ROLE_EDIT_FAILED;
        }

    }

    public ResponseCode adminRoleDelete(RoleDelForm roleDelForm) {

        try {
            AdminRoleEntity adminRoleEntity = adminRoleMapper.selectRoleById(Long.parseLong(roleDelForm.getRoleId()));

            if (null == adminRoleEntity) {
                return ResponseCode.ROLE_NOT_EXIST;
            }

            if (adminRoleEntity.isLock()) {
                return ResponseCode.ROLE_CANT_DEL;
            }

            long delResources = adminRoleMapper.deleteRoleAllResource(adminRoleEntity.getId());
            long delRole = adminRoleMapper.deleteRole(adminRoleEntity);

            if (delResources != 0 && delRole != 0) {
                return ResponseCode.ROLE_DEL_SUCCESS;
            } else {
                return ResponseCode.ROLE_DEL_FAILED;
            }

        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseCode.ROLE_DEL_FAILED;
        }

    }

}
