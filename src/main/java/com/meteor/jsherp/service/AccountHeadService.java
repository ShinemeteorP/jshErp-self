package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.AccountHead;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_account_head(财务主表)】的数据库操作Service
* @createDate 2023-04-13 18:07:54
*/
public interface AccountHeadService extends IService<AccountHead> {

    /**
     * 根据billId获取只有billNo有值的AccountHead列表
     * @param billId
     * @return
     */
    List<AccountHead> getBillNoByBillId(Long billId);
}
