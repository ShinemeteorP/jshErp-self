package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.AccountItem;
import com.meteor.jsherp.mapper.AccountItemMapper;
import com.meteor.jsherp.service.AccountItemService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_account_item(财务子表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class AccountItemServiceImpl extends ServiceImpl<AccountItemMapper, AccountItem>
    implements AccountItemService {

}




