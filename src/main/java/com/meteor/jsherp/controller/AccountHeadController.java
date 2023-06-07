package com.meteor.jsherp.controller;

import com.meteor.jsherp.domain.AccountHead;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.AccountHeadService;
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
@RequestMapping("/accountHead")
@RestController
public class AccountHeadController {

    @Resource
    private AccountHeadService accountHeadService;

    @GetMapping("/getFinancialBillNoByBillId")
    public BaseResponse getFinancialBillNoByBillId(@RequestParam("billId") Long billId){
        BaseResponse response = new BaseResponse();
        try {
            List<AccountHead> list = accountHeadService.getBillNoByBillId(billId);
            ResponseUtil.resSuccess(response, list);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }

}
