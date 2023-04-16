package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Function;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.FunctionMapper;
import com.meteor.jsherp.service.FunctionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public JSONArray getMenuArray(UserBusiness userBusiness, String pNumber, Map<String, List<Function>> functionMap, boolean approvalFlag) {
        if (!functionMap.containsKey(pNumber)){
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        List<Function> functionList = functionMap.get(pNumber);
        for (Function function:
             functionList) {
            JSONObject object = new JSONObject();
            object.put("id", function.getId());
            object.put("text", function.getName());
            object.put("icon", function.getIcon());
            object.put("url", function.getUrl());
            object.put("component", function.getComponent());
            if(functionMap.containsKey(function.getNumber())){
                JSONArray menuArray = getMenuArray(userBusiness, function.getNumber(), functionMap, approvalFlag);
                if (menuArray != null){
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
}




