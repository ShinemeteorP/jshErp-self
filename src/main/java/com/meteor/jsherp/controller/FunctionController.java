package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Function;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.service.FunctionService;
import com.meteor.jsherp.service.SystemConfigService;
import com.meteor.jsherp.service.UserBusinessService;
import com.meteor.jsherp.utils.CommonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestController
@RequestMapping("/function")
public class FunctionController {

    @Resource
    private FunctionService functionService;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private SystemConfigService systemConfigService;

    private final Map<String, List<Function>> functionTreeMap;

    /**
     * 系统所有有子菜单的菜单编号
     */
    private final List<String> functionNumberList = Arrays.asList(
            "0", "0001", "0101", "0102", "0301", "0401", "0502", "0603", "0704", "0801");



    /**
     * 构造器初始化 储存菜单编号及其对应的子菜单列表的map
     */
    public FunctionController(FunctionService functionService, UserBusinessService userBusinessService,
                              SystemConfigService systemConfigService){
        this.functionService = functionService;
        this.userBusinessService = userBusinessService;
        this.systemConfigService = systemConfigService;
        ConcurrentHashMap<String, List<Function>> functionTreeMap = new ConcurrentHashMap<>();
        for (String number:
                functionNumberList) {
            functionTreeMap.put(number, functionService.getListByParent(number));
        }
        this.functionTreeMap = functionTreeMap;


    }

    /**
     * 查询获取用户有权限的菜单列表
     * @param object
     * @return 带有菜单url、text、子菜单等信息的数组
     */
    @PostMapping("/findMenuByPNumber")
    public JSONArray getMenuByPNumber(@RequestBody JSONObject object){
        JSONArray menuArray = null;
        try {
            String pNumber = object.getString("pNumber");
            Long userId = object.getLong("userId");
            UserBusiness userRole = userBusinessService.getOneByKeyId(userId, UserConstant.USER_BUSINESS_USER_ROLE);

            UserBusiness userFunction = userBusinessService.getOneByKeyId(
                    CommonUtil.getNumber(userRole.getValue()), UserConstant.USER_BUSINESS_ROLE_FUNCTION);
            menuArray = functionService.getMenuArray(userFunction, pNumber, functionTreeMap, true);
            JSONObject homeItem = new JSONObject();
            homeItem.put("id", 0);
            homeItem.put("text", "首页");
            homeItem.put("icon", "home");
            homeItem.put("url", "/dashboard/analysis");
            homeItem.put("component", "/layouts/TabLayout");
            menuArray.add(0,homeItem);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return menuArray;
    }
}
