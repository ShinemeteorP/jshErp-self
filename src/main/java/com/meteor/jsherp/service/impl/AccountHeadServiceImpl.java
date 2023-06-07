package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.AccountHead;
import com.meteor.jsherp.mapper.AccountHeadMapper;
import com.meteor.jsherp.service.AccountHeadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_account_head(财务主表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class AccountHeadServiceImpl extends ServiceImpl<AccountHeadMapper, AccountHead>
    implements AccountHeadService {

    @Resource
    private AccountHeadMapper accountHeadMapper;

    @Override
    public List<AccountHead> getBillNoByBillId(Long billId) {
        return accountHeadMapper.getBillNoByBillId(billId);
    }
}




