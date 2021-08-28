package com.sp.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sp.admin.dao.AdminResourcesMapper;
import com.sp.admin.entity.authority.AdminResourcesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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

}
