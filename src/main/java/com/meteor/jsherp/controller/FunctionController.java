package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Function;
import com.meteor.jsherp.domain.SystemConfig;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.FunctionService;
import com.meteor.jsherp.service.UserBusinessService;
import com.meteor.jsherp.utils.CommonUtil;
import com.meteor.jsherp.utils.ResponseUtil;
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
    private SystemConfigController systemConfigController;

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
                              SystemConfigController systemConfigController) {
        this.functionService = functionService;
        this.userBusinessService = userBusinessService;
        this.systemConfigController = systemConfigController;
        ConcurrentHashMap<String, List<Function>> functionTreeMap = new ConcurrentHashMap<>();
        for (String number :
                functionNumberList) {
            functionTreeMap.put(number, functionService.getListByParent(number));
        }
        this.functionTreeMap = functionTreeMap;


    }

    /**
     * 查询获取用户有权限的菜单列表
     *
     * @param object
     * @return 带有菜单url、text、子菜单等信息的数组
     */
    @PostMapping("/findMenuByPNumber")
    public JSONArray getMenuByPNumber(@RequestBody JSONObject object) {

        JSONArray menuArray = null;

        String pNumber = object.getString("pNumber");
        Long userId = object.getLong("userId");
        UserBusiness userRole = userBusinessService.getOneByKeyId(userId, UserConstant.USER_BUSINESS_USER_ROLE);

        List<Long> longs = CommonUtil.getNumberWithMid(userRole.getValue());
        if (longs.size() != 1) {
            throw new RuntimeException("用户权限异常，userRole类value个数大于1");
        }
        long keyId = longs.get(0);
        UserBusiness userFunction = userBusinessService.getOneByKeyId(keyId
                , UserConstant.USER_BUSINESS_ROLE_FUNCTION);
        SystemConfig systemConfig = systemConfigController.getCurrent();

        menuArray = functionService.getMenuArray(userFunction, pNumber, functionTreeMap, systemConfig.getMultiLevelApprovalFlag());
        JSONObject homeItem = new JSONObject();
        homeItem.put("id", 0);
        homeItem.put("text", "首页");
        homeItem.put("icon", "home");
        homeItem.put("url", "/dashboard/analysis");
        homeItem.put("component", "/layouts/TabLayout");
        menuArray.add(0, homeItem);

        return menuArray;
    }

    /**
     * 根据type和id获取菜单列表，包含了check属性，表示用户是否有权限
     *
     * @param UBType
     * @param UBKeyId 用户id
     * @param token
     * @return
     */
    @GetMapping("/findRoleFunction")
    public JSONArray findRoleFunction(@RequestParam("UBType") String UBType,
                                      @RequestParam("UBKeyId") String UBKeyId,
                                      @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        JSONArray obj = null;

        Long tentId = CommonUtil.getTenantIdByToken(token);
        obj = functionService.findRoleFunction(UBType, UBKeyId, functionTreeMap, tentId);

        return obj;
    }

    /**
     * 根据id查询用户拥有权限的菜单信息
     *
     * @param keyId
     * @return
     */
    @GetMapping("/findRoleFunctionsById")
    public BaseResponse findRoleFunctionsById(@RequestParam("roleId") String keyId) {
        BaseResponse response = new BaseResponse();

        Map data = functionService.findRoleFunctionsById(keyId);
        ResponseUtil.resSuccess(response, data);

        return response;
    }


}
