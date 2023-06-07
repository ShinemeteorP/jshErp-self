package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meteor.jsherp.domain.AccountItem;
import com.meteor.jsherp.mapper.AccountItemMapper;
import org.springframework.stereotype.Service;
import com.meteor.jsherp.service.AccountItemService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_account_item(财务子表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class AccountItemServiceImpl extends CommonServiceImpl<AccountItemMapper, AccountItem>
    implements AccountItemService {

    @Resource
    private AccountItemMapper accountItemMapper;

    @Override
    public Map<Long, Long> getBillCountMap(ArrayList<Long> idList) {
        QueryWrapper<AccountItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("bill_id", idList);
        List<AccountItem> accountItemList = accountItemMapper.selectList(queryWrapper);
        if(accountItemList == null || accountItemList.isEmpty()){
            return null;
        }
        Map<Long, Long> map = new HashMap<>();
        for (AccountItem a:
             accountItemList) {
            map.put(a.getBillId(), Long.valueOf(accountItemList.size()));
        }
        return map;
    }

    @Override
    public BigDecimal getCompletedDebt(Long id) {

        return accountItemMapper.getEachAmountByBillId(id).abs();
    }
}




