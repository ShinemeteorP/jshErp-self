package com.meteor.jsherp.controller;

import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.SequenceService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestController
@RequestMapping("/sequence")
public class SequenceController {

    @Resource
    private SequenceService sequenceService;

    /**
     * 获取单据编号
     * @return
     */
    @GetMapping("/buildNumber")
    public BaseResponse buildNumber(){
        BaseResponse response = new BaseResponse();
        try {
            String numberStr = sequenceService.buildNumber();
            HashMap<String, Object> map = new HashMap<>();
            map.put("defaultNumber", numberStr);
            ResponseUtil.resSuccess(response, map);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }

}
