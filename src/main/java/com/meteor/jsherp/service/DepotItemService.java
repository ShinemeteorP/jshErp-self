package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.DepotItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_depot_item(单据子表)】的数据库操作Service
* @createDate 2023-04-13 18:07:54
*/
public interface DepotItemService extends IService<DepotItem>, CommonService<DepotItem> {

    /**
     * 获取月度各维度达成数据
     * @param token
     * @param type
     * @param approvalFlag
     * @return
     * @throws Exception
     */
    Map<String, Object> getMonthsStatics(String token, String type, String approvalFlag) throws Exception;


    /**
     * 获取订单号对应的物料operNumber之和
     * @param idList
     * @return
     */
    Map<Long, BigDecimal> getMaterialCountListMap(List<Long> idList);
}
