package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Account;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_account(账户信息)】的数据库操作Service
* @createDate 2023-04-13 18:07:54
*/
public interface AccountService extends IService<Account>, CommonService<Account> {

    /**
     * 获取租户下所有可用Account
     * @return
     */
    List<Account> getAccountList();

    /**
     * 获取该租户下，可用的所有Account的id和name，
     * @return  id为key，name为value的map
     */
    Map<Long, String> getAccountIdAndName();

    /**
     * 将账户名称和金额拼接在一起
     * @param accountMap
     * @param accountIdList
     * @param accountMoneyList
     * @return
     */
    String getAccountStrByIdAndMoney(Map<Long, String> accountMap, String accountIdList, String accountMoneyList);
}
