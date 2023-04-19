package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.DepotHead;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import java.math.BigDecimal;

/**
* @author 刘鑫
* @description 针对表【jsh_depot_head(单据主表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:54
* @Entity generator.domain.DepotHead
*/
public interface DepotHeadMapper extends BaseMapper<DepotHead> {
    /**
     * 根据筛选条件获取销售数据，统计discount_last_money  列为账务数据
     * @param type 类型，对应type列
     * @param subType 子类， 对应sub_type类
     * @param hasSupplier 是否有供应商
     * @param startTime 开始时间  筛选 oper_time 列
     * @param endTime
     * @param userIds
     * @param approval
     * @return
     */
    BigDecimal getAnySaleStatistics(String type, String subType, int hasSupplier, String startTime, String endTime, long[] userIds, String approval);


    /**
     * 根据筛选条件获取销售数据，统计total_money(并且是绝对值)  列为账务数据
     * @param type 类型，对应type列
     * @param subType 子类， 对应sub_type类
     * @param hasSupplier 是否有供应商
     * @param startTime 开始时间  筛选 oper_time 列
     * @param endTime
     * @param userIds
     * @param approval
     * @return
     */
    BigDecimal getRetailSaleStatistics(String type, String subType, int hasSupplier, String startTime, String endTime, long[] userIds, String approval);
}




