package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.AccountItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_account_item(财务子表)】的数据库操作Service
* @createDate 2023-04-13 18:07:54
*/
public interface AccountItemService extends IService<AccountItem>, CommonService<AccountItem> {

    /**
     * 获取对应的billid列表的记录条数
     * @param idList
     * @return
     */
    Map<Long, Long> getBillCountMap(ArrayList<Long> idList);
}
