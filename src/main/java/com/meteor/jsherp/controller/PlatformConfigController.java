package com.meteor.jsherp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.PlatformConfig;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.PlatformConfigService;
import com.meteor.jsherp.utils.ResponseUtil;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestController
@RequestMapping("/platformConfig")
public class PlatformConfigController {
    @Resource
    private PlatformConfigService platformConfigService;

    @GetMapping("/getPlatform/name")
    public String getPlatformName(){
        PlatformConfig config = null;
        try {
            config = platformConfigService.getPlatformConfigByKey(ErpAllConstant.PLATFORM_NAME);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "ERP系统";
        }
        return config.getPlatformValue();
    }

    @GetMapping("/getPlatform/url")
    public String getPlatformUrl(){
        PlatformConfig config = null;
        try {
            config = platformConfigService.getPlatformConfigByKey(ErpAllConstant.PLATFORM_URL);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "#";
        }
        return config.getPlatformValue();
    }

    @GetMapping("/getPlatformConfigByKey")
    public BaseResponse getPlatformConfigByKey(@RequestParam("platformKey") String platformKey){
        BaseResponse response = new BaseResponse();
        try {
            PlatformConfig config = platformConfigService.getPlatformConfigByKey(platformKey);
            ResponseUtil.resSuccess(response, config);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }
}
