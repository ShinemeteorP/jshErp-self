package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.domain.Role;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.RoleService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/role")
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 批量修改角色的状态
     *
     * @param obj
     * @return
     */
    @PostMapping("/batchSetStatus")
    public BaseResponse batchSetStatus(@RequestBody JSONObject obj) {
        BaseResponse response = new BaseResponse();

        Boolean status = obj.getBoolean("status");
        String ids = obj.getString("ids");
        HashMap<Object, Object> data = new HashMap<>();
        int update = roleService.batchSetStatus(ids, status);
        if (update > 0) {
            ResponseUtil.resSuccessMsg(response, data, ExceptionConstant.SERVICE_SUCCESS_MSG);
        } else {
            ResponseUtil.customServiceExceptionResponse("修改失败", response);
        }


        return response;
    }


    @GetMapping("/allList")
    public List<Role> allList(){
        return roleService.getAllList();
    }
}
