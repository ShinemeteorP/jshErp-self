package com.meteor.jsherp.controller;

import com.meteor.jsherp.domain.Account;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.AccountService;
import com.meteor.jsherp.utils.ResponseUtil;
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
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 获取租户下所有可用Account
     * @return
     */
    @GetMapping("/getAccount")
    public BaseResponse getAccount(){
        BaseResponse response = new BaseResponse();
        try {
            HashMap<String, Object> map = new HashMap<>();
            List<Account> accountList = accountService.getAccountList();
            map.put("accountList", accountList);
            ResponseUtil.resSuccess(response, map);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }
}
