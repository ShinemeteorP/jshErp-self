package com.meteor.jsherp.controller;

import com.meteor.jsherp.domain.SystemConfig;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.SystemConfigService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/systemConfig")
@RestController
public class SystemConfigController {
    @Resource
    private SystemConfigService systemConfigService;

    private SystemConfig systemConfig;

    @GetMapping("/getCurrentInfo")
    public BaseResponse getSystemConfig(){
        BaseResponse response = new BaseResponse();
        try {
            if (systemConfig == null){
                systemConfig = systemConfigService.getOne(null);
            }
            ResponseUtil.resSuccess(response, systemConfig);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }

    public SystemConfig getCurrent(){
        if (systemConfig == null){
            getSystemConfig();
        }
        return systemConfig;
    }

}
