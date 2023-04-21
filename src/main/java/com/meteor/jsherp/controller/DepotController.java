package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.DepotService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/depot")
@RestController
public class DepotController {

    @Resource
    private DepotService depotService;

    @Resource
    private UserService userService;

    @Resource
    private SystemConfigController systemConfigController;

    @GetMapping("/findDepotByCurrentUser")
    public BaseResponse findDepotByCurrentUser(@RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token){
        BaseResponse response = new BaseResponse();
        try {
            User user = userService.getLoginUser(token);
            String depotFlag = systemConfigController.getCurrent().getDepotFlag();
            JSONArray array = depotService.findDepotByUserId(user.getId(), depotFlag);
            ResponseUtil.resSuccess(response, array);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }

        return response;
    }
}
