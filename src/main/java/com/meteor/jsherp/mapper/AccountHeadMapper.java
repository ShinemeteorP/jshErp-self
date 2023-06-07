package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.AccountHead;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_account_head(财务主表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:54
* @Entity generator.domain.AccountHead
*/
public interface AccountHeadMapper extends BaseMapper<AccountHead> {

    List<AccountHead> getBillNoByBillId(Long billId);
}




