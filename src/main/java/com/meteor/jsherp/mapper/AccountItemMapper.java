package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.AccountItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
* @author 刘鑫
* @description 针对表【jsh_account_item(财务子表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:54
* @Entity generator.domain.AccountItem
*/
public interface AccountItemMapper extends BaseMapper<AccountItem> {

    BigDecimal getEachAmountByBillId(Long id);
}




