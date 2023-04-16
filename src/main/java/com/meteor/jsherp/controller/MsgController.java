package com.meteor.jsherp.controller;

import com.meteor.jsherp.domain.Msg;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.MsgService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/msg")
@RestController
public class MsgController {

    @Resource
    private MsgService msgService;

    @GetMapping("/getMsgByStatus")
    public BaseResponse getMsgByStatus(@RequestParam("status") String status){

        BaseResponse response = new BaseResponse();
        try {
            List<Msg> msgList = msgService.getListByKey("status", status);
            ResponseUtil.resSuccess(response, msgList);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }
}
