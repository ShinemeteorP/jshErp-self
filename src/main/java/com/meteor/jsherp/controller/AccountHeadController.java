package com.meteor.jsherp.controller;

import com.meteor.jsherp.domain.AccountHead;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.AccountHeadService;
import com.meteor.jsherp.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api("财务主表")
public class AccountHeadController {

    @Resource
    private AccountHeadService accountHeadService;

    @GetMapping("/getFinancialBillNoByBillId")
    @ApiOperation("根据billId获取只有billNo有值的AccountHead列表")
    public BaseResponse getFinancialBillNoByBillId(@RequestParam("billId") @ApiParam("账单id") Long billId ){
        BaseResponse response = new BaseResponse();

        List<AccountHead> list = accountHeadService.getBillNoByBillId(billId);
        ResponseUtil.resSuccess(response, list);

        return response;
    }

}
