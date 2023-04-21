package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.DepotHead;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_depot_head(单据主表)】的数据库操作Service
* @createDate 2023-04-13 18:07:54
*/
public interface DepotHeadService extends IService<DepotHead>, CommonService<DepotHead> {

    /**
     * 根据用户权限、组织配置等 获取对应口径的今天、昨天、这个月、今年的采购、销售、零售金额
     * @param userIds 用户id列表，来匹配创建订单的创建者
     * @param userId 当前登入用户id
     * @param approval 目前组织是否开启多级审核标识
     * @return 获取今天、昨天、这个月、今年的采购、销售、零售金额
     */
    Map<String, Object> getBuyAndSaleStatistics(long[] userIds, long userId, String approval);

    /**
     * 获取对应的采购入库金额
     * @param hasSupplier 是否有供应商
     * @param startTime 查询时间段起始时间
     * @param endTime 查询时间段最大时间
     * @param userIds 用户id列表，来匹配创建订单的创建者
     * @param approval 目前组织是否开启多级审核标识
     * @return 采购入库金额
     */
    BigDecimal getBuyInput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval);

    /**
     * 获取对应的采购出库金额
     * @param hasSupplier 是否有供应商
     * @param startTime 查询时间段起始时间
     * @param endTime 查询时间段最大时间
     * @param userIds 用户id列表，来匹配创建订单的创建者
     * @param approval 目前组织是否开启多级审核标识
     * @return 采购出库金额
     */
    BigDecimal getBuyOutput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval);

    /**
     * 获取对应的销售退货金额
     * @param hasSupplier 是否有供应商
     * @param startTime 查询时间段起始时间
     * @param endTime 查询时间段最大时间
     * @param userIds 用户id列表，来匹配创建订单的创建者
     * @param approval 目前组织是否开启多级审核标识
     * @return 销售退货金额
     */
    BigDecimal getSaleInput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval);

    /**
     * 获取对应的销售金额
     * @param hasSupplier 是否有供应商
     * @param startTime 查询时间段起始时间
     * @param endTime 查询时间段最大时间
     * @param userIds 用户id列表，来匹配创建订单的创建者
     * @param approval 目前组织是否开启多级审核标识
     * @return 销售金额
     */
    BigDecimal getSaleOutput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval);

    /**
     * 获取对应的零售退货金额
     * @param hasSupplier 是否有供应商
     * @param startTime 查询时间段起始时间
     * @param endTime 查询时间段最大时间
     * @param userIds 用户id列表，来匹配创建订单的创建者
     * @param approval 目前组织是否开启多级审核标识
     * @return 零售退货金额
     */
    BigDecimal getRetailSaleInput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval);

    /**
     * 获取对应的零售金额
     * @param hasSupplier 是否有供应商
     * @param startTime 查询时间段起始时间
     * @param endTime 查询时间段最大时间
     * @param userIds 用户id列表，来匹配创建订单的创建者
     * @param approval 目前组织是否开启多级审核标识
     * @return 零售金额
     */
    BigDecimal getRetailSaleOutput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval);

    /**
     * 获取各个number对应的size值
     * @param numberList
     * @return
     */
    Map<String, Long> getBillSizeMap(List<String> numberList);
}
