package com.sp.admin.service;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sp.admin.commonutil.response.ResponseCode;
import com.sp.admin.commonutil.response.ServerResponse;
import com.sp.admin.dao.AdminMapper;
import com.sp.admin.dao.AdminResourcesMapper;
import com.sp.admin.dao.AdminRoleMapper;
import com.sp.admin.entity.authority.AdminEntity;
import com.sp.admin.entity.authority.AdminResourcesEntity;
import com.sp.admin.forms.authority.ResourceAddForm;
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
public class AdminResourcesService {

    @Autowired
    AdminResourcesMapper adminResourcesMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AdminRoleMapper adminRoleMapper;

    public JSONObject getMenuJsonByAdminId(Long adminId) {

        List<AdminResourcesEntity> adminResourcesList = adminResourcesMapper.selectAdminResourcesByAdminId(adminId);

        JSONObject menuJson = new JSONObject();

        JSONArray topLevelMenuJson = new JSONArray();
        for (AdminResourcesEntity topLevelResources : adminResourcesList) {
            JSONObject topLevelMenu = new JSONObject();
            if ((0 == topLevelResources.getSourceType() || 1 == topLevelResources.getSourceType()) && topLevelResources.isEnabled() && null == topLevelResources.getSourcePid()) {
                topLevelMenu.put("id", topLevelResources.getId());
                topLevelMenu.put("order", topLevelResources.getSourceOrder());
                topLevelMenu.put("url", topLevelResources.getSourceUrl());
                topLevelMenu.put("name", topLevelResources.getSourceName());
                topLevelMenu.put("iconfont", topLevelResources.getIconfont());
                topLevelMenuJson.add(topLevelMenu);
            }
            JSONArray senondLevelMenuJson = new JSONArray();
            for (AdminResourcesEntity secondLevelResources : adminResourcesList) {
                JSONObject secondLevelMenu = new JSONObject();
                if ((0 == secondLevelResources.getSourceType() || 1 == secondLevelResources.getSourceType()) && secondLevelResources.isEnabled() && null != secondLevelResources.getSourcePid() && secondLevelResources.getSourcePid().equals(topLevelResources.getId())) {
                    secondLevelMenu.put("id", secondLevelResources.getId());
                    secondLevelMenu.put("order", secondLevelResources.getSourceOrder());
                    secondLevelMenu.put("url", secondLevelResources.getSourceUrl());
                    secondLevelMenu.put("name", secondLevelResources.getSourceName());
                    secondLevelMenu.put("iconfont", secondLevelResources.getIconfont());
                    senondLevelMenuJson.add(secondLevelMenu);
                }
                JSONArray threeLevelMenuJson = new JSONArray();
                for (AdminResourcesEntity threeLevelResources : adminResourcesList) {
                    JSONObject threeLevelMenu = new JSONObject();
                    if ((0 == threeLevelResources.getSourceType() || 1 == threeLevelResources.getSourceType()) && threeLevelResources.isEnabled() && null != threeLevelResources.getSourcePid() && threeLevelResources.getSourcePid().equals(secondLevelResources.getId())) {
                        threeLevelMenu.put("id", threeLevelResources.getId());
                        threeLevelMenu.put("order", threeLevelResources.getSourceOrder());
                        threeLevelMenu.put("url", threeLevelResources.getSourceUrl());
                        threeLevelMenu.put("name", threeLevelResources.getSourceName());
                        threeLevelMenu.put("iconfont", threeLevelResources.getIconfont());
                        threeLevelMenuJson.add(threeLevelMenu);
                    }
                }
                threeLevelMenuJson.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger("order")));
                secondLevelMenu.put("sub", threeLevelMenuJson);
            }
            senondLevelMenuJson.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger("order")));
            topLevelMenu.put("sub", senondLevelMenuJson);
        }
        topLevelMenuJson.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger("order")));
        menuJson.put("menu", topLevelMenuJson);

        return menuJson;

    }

    public List<Map<String, String>> getAllParentResources() {

        List<AdminResourcesEntity> allResourcesList = adminResourcesMapper.selectAllResources();

        List<Map<String, String>> allParentResources = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("id", "0");
        map.put("name", "顶级资源");
        map.put("type", "0");
        allParentResources.add(map);
        for (AdminResourcesEntity topResource : allResourcesList) {
            if (null == topResource.getSourcePid()) {
                Map<String, String> topMap = new HashMap<>();
                topMap.put("id", topResource.getId().toString());
                topMap.put("type", String.valueOf(topResource.getSourceType()));
                topMap.put("name", "┝ " + topResource.getSourceName());
                allParentResources.add(topMap);
                for (AdminResourcesEntity secondResource : allResourcesList) {
                    if ((0 == secondResource.getSourceType() || 1 == secondResource.getSourceType()) && null != secondResource.getSourcePid() && secondResource.getSourcePid().equals(topResource.getId())) {
                        Map<String, String> secondMap = new HashMap<>();
                        secondMap.put("id", secondResource.getId().toString());
                        secondMap.put("type", String.valueOf(secondResource.getSourceType()));
                        secondMap.put("name", "&nbsp;&nbsp;┝ " + secondResource.getSourceName());
                        allParentResources.add(secondMap);
                        for (AdminResourcesEntity threeResource : allResourcesList) {
                            if ((0 == threeResource.getSourceType() || 1 == threeResource.getSourceType() )&& null != threeResource.getSourcePid() && threeResource.getSourcePid().equals(secondResource.getId())) {
                                Map<String, String> threeMap = new HashMap<>();
                                threeMap.put("id", threeResource.getId().toString());
                                threeMap.put("type", String.valueOf(threeResource.getSourceType()));
                                threeMap.put("name", "&nbsp;&nbsp;&nbsp;&nbsp;┝ " + threeResource.getSourceName());
                                allParentResources.add(threeMap);
                            }
                        }
                    }
                }
            }
        }

        return allParentResources;

    }

    public List<AdminResourcesEntity> getAdminResourceList() {
        return adminResourcesMapper.selectAllResources();
    }

    public ResponseCode adminResourceSave(ResourceAddForm resourceAddForm) {

        try {
            AdminResourcesEntity parentResource = null;
            AdminResourcesEntity newResource = new AdminResourcesEntity();

            if ("0".equals(resourceAddForm.getResourcePid())) {
                newResource.setSourcePid(null);
            } else {
                parentResource = adminResourcesMapper.selectResourceById(Long.parseLong(resourceAddForm.getResourcePid()));
                if (null == parentResource) {
                    return ResponseCode.RESOURCE_PARENT_IS_NULL;
                }
                newResource.setSourcePid(parentResource.getId());
            }
            newResource.setSourceType(Integer.parseInt(resourceAddForm.getResourceType()));
            newResource.setEnabled(null != resourceAddForm.getEnable() && "1".equals(resourceAddForm.getEnable()));
            newResource.setLock(false);
            newResource.setSourceName(resourceAddForm.getResourceName());
            newResource.setSourceUrl(resourceAddForm.getResourceUrl());
            newResource.setSourceFunction(resourceAddForm.getResourceFun());
            newResource.setSourceOrder(Integer.parseInt(resourceAddForm.getResourceOrder()));
            newResource.setIconfont(resourceAddForm.getIconfont());
            newResource.setVersion(1L);
            newResource.setWhenCreated(DateTime.now().toTimestamp());
            newResource.setWhenUpdated(DateTime.now().toTimestamp());

            long insertRow = adminResourcesMapper.insertResource(newResource);
            AdminEntity superAdmin = adminMapper.selectSuperAdminInfo();
            long superAdminRoleId = superAdmin.getRoleId();
            long insertRoleResourceRow = adminRoleMapper.insertRoleResource(superAdminRoleId, newResource.getId());

            if (insertRow != 0 && insertRoleResourceRow != 0) {
                return ResponseCode.RESOURCE_ADD_SUCCESS;
            } else {
                return ResponseCode.RESOURCE_ADD_FAILED;
            }

        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseCode.RESOURCE_ADD_FAILED;
        }


    }


}
