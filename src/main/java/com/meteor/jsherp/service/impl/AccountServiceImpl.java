package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Account;
import com.meteor.jsherp.mapper.AccountMapper;
import com.meteor.jsherp.service.AccountService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_account(账户信息)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService {

}




