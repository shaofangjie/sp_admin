package com.sp.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sp.admin.dao.AdminResourcesMapper;
import com.sp.admin.entity.authority.AdminResourcesEntity;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j
@Service
public class AdminResourcesService {

    @Autowired
    AdminResourcesMapper adminResourcesMapper;

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



}
