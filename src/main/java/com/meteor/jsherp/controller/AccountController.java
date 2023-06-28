package com.meteor.jsherp.controller;

import com.meteor.jsherp.domain.Account;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.AccountService;
import com.meteor.jsherp.utils.ResponseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/account")
@RestController
@Api(tags = "账户管理" , value = "账户管理控制器")
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 获取租户下所有可用Account
     * @return
     */
    @GetMapping("/getAccount")
    @ApiOperation("获取租户下所有可用Account")
    public BaseResponse getAccount(){
        BaseResponse response = new BaseResponse();

        HashMap<String, Object> map = new HashMap<>();
        List<Account> accountList = accountService.getAccountList();
        map.put("accountList", accountList);
        ResponseUtil.resSuccess(response, map);
        return response;
    }
}
