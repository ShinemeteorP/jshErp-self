package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.DepotService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/depot")
@RestController
@Api("仓库管理")
public class DepotController {

    @Resource
    private DepotService depotService;

    @Resource
    private UserService userService;

    @Resource
    private SystemConfigController systemConfigController;

    @GetMapping("/findDepotByCurrentUser")
    @ApiOperation("根据当前登入用户获取有权限的仓库列表")
    public BaseResponse findDepotByCurrentUser(@RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) @ApiParam("登入状态码") String token) {
        BaseResponse response = new BaseResponse();

        User user = userService.getLoginUser(token);
        String depotFlag = systemConfigController.getCurrent().getDepotFlag();
        JSONArray array = depotService.findDepotByUserId(user.getId(), depotFlag);
        ResponseUtil.resSuccess(response, array);

        return response;
    }
}
