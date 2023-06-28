package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.controller.SystemConfigController;
import com.meteor.jsherp.domain.Function;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.FunctionMapper;
import com.meteor.jsherp.service.FunctionService;
import com.meteor.jsherp.service.UserBusinessService;
import com.meteor.jsherp.utils.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_function(功能模块表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class FunctionServiceImpl extends ServiceImpl<FunctionMapper, Function>
    implements FunctionService {

    @Resource
    private FunctionMapper functionMapper;

    @Resource
    private SystemConfigController systemConfigController;

    @Resource
    private UserBusinessService userBusinessService;

    @Override
    public JSONArray getMenuArray(UserBusiness userBusiness, String pNumber, Map<String, List<Function>> functionMap, String approvalFlag) {
        if (!functionMap.containsKey(pNumber)){
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        List<Function> functionList = functionMap.get(pNumber);
        for (Function function:
             functionList) {
            //如果关闭多级审核，遇到任务审核菜单直接跳过
            if (BusinessConstant.SYSTEM_CONFIG_APPROVAL_CLOSE.equals(approvalFlag) && "/workflow".equals(function.getUrl())) {
                continue;
            }
            JSONObject object = new JSONObject();
            object.put("id", function.getId());
            object.put("text", function.getName());
            object.put("icon", function.getIcon());
            object.put("url", function.getUrl());
            object.put("component", function.getComponent());
            if(functionMap.containsKey(function.getNumber())){
                JSONArray menuArray = getMenuArray(userBusiness, function.getNumber(), functionMap, approvalFlag);
                if (menuArray != null && menuArray.size() > 0){
                    object.put("children", menuArray);
                    jsonArray.add(object);
                }
            }else if (userBusiness.getValue().contains("[" + function.getId() + "]")){
                jsonArray.add(object);
            }
        }
        return jsonArray;
    }


    @Override
    public List<Function> getListByParent(String pNumber) {
        QueryWrapper<Function> queryWrapper = new QueryWrapper<Function>().
                eq("parent_number", pNumber).eq("enabled", true).orderByAsc("sort");
        return functionMapper.selectList(queryWrapper);
    }

    @Override
    public JSONArray findRoleFunction(String ubType, String ubKeyId, Map<String, List<Function>> functionTreeMap, Long tentId) {
        JSONArray jsonArray = new JSONArray();
        List<Function> functions = functionTreeMap.get("0");
        JSONObject object = new JSONObject();
        object.put("id", 0);
        object.put("key", 0);
        object.put("value", 0);
        object.put("title", "功能列表");
        object.put("attributes", "功能列表");
        String multiLevelApprovalFlag = systemConfigController.getCurrent().getMultiLevelApprovalFlag();
        if(functions != null){
            List<Function> functionList = new ArrayList<>();
            for (Function f:
                 functions) {
                if("0".equals(multiLevelApprovalFlag) && "/workflow".equals(f.getUrl())){
                    continue;
                }
                if(tentId != 0L){
                    if(!"系统管理".equals(f.getName())){
                        functionList.add(f);
                    }
                }else{
                    //超管
                    functionList.add(f);
                }
            }
            UserBusiness userBusiness = userBusinessService.getOneByKeyMap(ImmutableMap.of("type", ubType, "key_id", ubKeyId));
            String value = "";
            if(userBusiness != null) {
                value = userBusiness.getValue();
            }
            //过滤掉用户没有权限的菜单
            JSONArray array = selectFunctionListByUser(functionTreeMap, functionList, value);
            object.put("children", array);
        }
        jsonArray.add(object);
        return jsonArray;
    }

    @Override
    public Map findRoleFunctionsById(String keyId) {

        UserBusiness userBusiness = userBusinessService.getOneByKeyMap(ImmutableMap.of("type", UserConstant.USER_BUSINESS_ROLE_FUNCTION, "key_id", keyId));
        if(userBusiness != null){
            HashMap<Object, Object> data = new HashMap<>();
            String btnStr = userBusiness.getBtnStr();
            HashMap<Object, Object> btnMap = new HashMap<>();
            JSONArray btnArray = JSONArray.parseArray(btnStr);
            if (btnArray != null) {
                for (Object obj:
                     btnArray) {
                    JSONObject jsonObject = JSONObject.parseObject(obj.toString());
                    if(jsonObject.get("funId") != null && jsonObject.get("btnStr") != null){
                        btnMap.put(jsonObject.getLong("funId"), jsonObject.getString("btnStr"));
                    }
                }
            }
            List<Long> ids = CommonUtil.getNumberWithMid(userBusiness.getValue());
            QueryWrapper<Function> queryWrapper = new QueryWrapper<Function>().in("id", ids).isNotNull("push_btn").
                    ne("push_btn", "").eq("enabled", true).orderByAsc("sort");
            List<Function> functions = functionMapper.selectList(queryWrapper);
            JSONArray array = new JSONArray();
            if(functions != null){
                for (Function f:
                     functions) {
                    JSONObject object = new JSONObject();
                    object.put("id", f.getId());
                    object.put("name", f.getName());
                    object.put("pushBtn", f.getPushBtn());
                    object.put("btnStr", btnMap.get(f.getId()));
                    array.add(object);
                }
            }
            data.put("rows", array);
            return data;
        }
        return null;
    }

    //过滤掉用户没有权限的菜单
    private JSONArray selectFunctionListByUser(Map<String, List<Function>> functionTreeMap, List<Function> functionList, String value) {
        JSONArray array = new JSONArray();
        for (Function f:
             functionList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", f.getId());
            jsonObject.put("key", f.getId());
            jsonObject.put("value", f.getId());
            jsonObject.put("title", f.getName());
            jsonObject.put("attributes", f.getName());
            List<Function> functions = functionTreeMap.get(f.getNumber());
            if (functions != null && functions.size() > 0){
                JSONArray children = selectFunctionListByUser(functionTreeMap, functions, value);
                jsonObject.put("children", children);
                array.add(jsonObject);
            }else{
                boolean contains = value.contains("[" + f.getId() + "]");
                jsonObject.put("checked", contains);
                array.add(jsonObject);
            }

        }
        return array;
    }
}




