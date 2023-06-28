package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.UserBusinessService;
import com.meteor.jsherp.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestController
@RequestMapping("/userBusiness")
@Api("权限管理")
public class UserBusinessController {

    @Resource
    private UserBusinessService userBusinessService;

    /**
     * 根据type和keyId返回对应的UserBusiness的id
     *
     * @param type
     * @param keyId
     * @return
     */
    @GetMapping("/checkIsValueExist")
    @ApiOperation("根据type和keyId返回对应的UserBusiness的id")
    public BaseResponse checkIsValueExist(@RequestParam(value = "type", required = false) String type,
                                          @RequestParam(value = "keyId", required = false) Long keyId) {
        BaseResponse response = new BaseResponse();
        HashMap<Object, Object> data = new HashMap<>();

        Long id = userBusinessService.checkIsValueExist(type, keyId);
        data.put("id", id);
        ResponseUtil.resSuccessMsg(response, data, ExceptionConstant.SERVICE_SUCCESS_MSG);


        return response;

    }

    /**
     * 修改用户的按钮权限
     *
     * @param body
     * @return
     */
    @PostMapping("/updateBtnStr")
    @ApiOperation("修改用户的按钮权限")
    public BaseResponse updateBtnStr(@RequestBody JSONObject body) {
        BaseResponse response = new BaseResponse();

        String btnStr = body.getString("btnStr");
        Long roleId = body.getLong("roleId");
        int update = userBusinessService.updateBtnStr(btnStr, roleId, UserConstant.USER_BUSINESS_ROLE_FUNCTION);
        if (update > 0) {
            ResponseUtil.resSuccessMsg(response, ExceptionConstant.SERVICE_SUCCESS_MSG);
        } else {
            response.setCode(500);
            response.setData("修改权限失败");
        }

        return response;
    }
}
