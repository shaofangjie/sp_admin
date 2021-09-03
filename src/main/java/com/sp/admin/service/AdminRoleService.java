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
import com.sp.admin.forms.authority.RoleSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AdminRoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminResourcesService adminResourcesService;
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

    public JSONObject getResourceTreeJson(AdminRoleEntity adminRole) {


        List<AdminResourcesEntity> allResourcesList = adminResourcesMapper.selectAllEnableResources();

        List<AdminResourcesEntity> hasResourcesList = new ArrayList<>();

        if (null != adminRole) {
            hasResourcesList = adminResourcesMapper.selectAdminResourcesByRoleId(adminRole.getId());
        }

        JSONArray resourcesTreeArr = new JSONArray();
        for (AdminResourcesEntity topResource : allResourcesList) {
            if (null == topResource.getSourcePid()) {
                JSONObject topMap = new JSONObject();
                topMap.put("value", topResource.getId().toString());
                topMap.put("name", topResource.getSourceName());
                topMap.put("order", topResource.getSourceOrder());
                topMap.put("checked", hasResourcesList.contains(topResource));
                JSONArray topList = new JSONArray();
                for (AdminResourcesEntity secondResource : allResourcesList) {
                    if (secondResource.isEnabled() && null != secondResource.getSourcePid() && secondResource.getSourcePid().equals(topResource.getId())) {
                        JSONObject secondMap = new JSONObject();
                        secondMap.put("value", secondResource.getId().toString());
                        secondMap.put("name", secondResource.getSourceName());
                        secondMap.put("order", secondResource.getSourceOrder());
                        secondMap.put("checked", hasResourcesList.contains(secondResource));
                        JSONArray secondList = new JSONArray();
                        for (AdminResourcesEntity threeResource : allResourcesList) {
                            if (threeResource.isEnabled() && null != threeResource.getSourcePid() && threeResource.getSourcePid().equals(secondResource.getId())) {
                                JSONObject threeMap = new JSONObject();
                                threeMap.put("value", threeResource.getId().toString());
                                threeMap.put("name", threeResource.getSourceName());
                                threeMap.put("order", threeResource.getSourceOrder());
                                threeMap.put("checked", hasResourcesList.contains(threeResource));
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

    @Transactional
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
            return ResponseCode.ROLE_ADD_FAILED;
        }

    }



}
