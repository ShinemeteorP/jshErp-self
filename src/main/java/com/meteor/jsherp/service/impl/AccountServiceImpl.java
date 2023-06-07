package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Account;
import com.meteor.jsherp.mapper.AccountMapper;
import com.meteor.jsherp.service.AccountService;
import com.meteor.jsherp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_account(账户信息)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class AccountServiceImpl extends CommonServiceImpl<AccountMapper, Account>
    implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public List<Account> getAccountList() {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>()
                .eq("enabled", true)
                .orderByAsc("sort").orderByDesc("id");
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        return accounts;
    }

    @Override
    public Map<Long, String> getAccountIdAndName() {
        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        List<Account> accounts = accountMapper.selectList(
                new QueryWrapper<Account>().eq("enabled", true)
                        .orderByAsc("sort").orderByDesc("id"));
        Map<Long, String> res = new HashMap<>();
        for (Account a :
                accounts) {
            res.put(a.getId(), a.getName());
        }
        return res;
    }

    @Override
    public String getAccountStrByIdAndMoney(Map<Long, String> accountMap, String accountIdList, String accountMoneyList) {
        List<Long> longList = StringUtil.strToLongList(accountIdList);
        List<BigDecimal> bigDecimals = StringUtil.strToBigDecimalList(accountMoneyList);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < longList.size(); i++){
            String name = accountMap.get(longList.get(i));
            BigDecimal money = bigDecimals.get(i).abs();
            sb.append(name + "(" + money + "元) ");
        }

        return sb.toString();
    }
}




