package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.DepotHead;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.extand.DepotHeadExReBody;
import com.meteor.jsherp.domain.extand.DepotHeadEx;
import org.springframework.transaction.annotation.Transactional;

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
     * 获取退款单的数量
     * 通过linkNumber获取各个单据，获取单据的number（key）及其对应的size值（value）
     * @param numberList
     * @return
     */
    Map<String, Long> getBillSizeMapByLinkNumber(List<String> numberList);

    /**
     * 新增一条单据记录
     * @param depotHeadArg
     */
    void addDepotHeadAndDetail(DepotHeadExReBody depotHeadArg, String token);


    /**
     * 针对退货单进行欠款检验
     * @param info
     * @param depotHead
     */
    void checkDebtByParam(String info, DepotHead depotHead);

    /**
     * 退货单对应的原单实际欠款（这里面要除去收付款的金额）
     * @param linkNumber 原单单号
     * @param number 当前单号
     * @return
     */
    BigDecimal getOriginalRealDebt(String linkNumber, String number);

    /**
     * 根据number获取DepotHeadExList
     * @param number
     * @return
     */
    List<DepotHeadEx> getDepotHeadExListByNumber(String number, String token);

    /**
     * 根据方法体数据，对单据进行核验和更新
     * @param depotHeadBody
     * @param token
     */
    @Transactional(rollbackFor = Exception.class)
    void updateDepotHeadAndDetail(DepotHeadExReBody depotHeadBody, String token);


    /**
     * 根据id列表批量删除单据
     * @param ids
     * @param token
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    int deleteBatchIds(String ids, String token);

    /**
     * 将关联的单据置为审核状态-针对采购入库、销售出库和盘点复盘，
     * 如果有linknumber，type和删除订单相同，但是number不同的单据，修改的状态就设置为3，不然就是1
     * @param linkNumber
     * @param number
     * @param type
     * @return 修改行数
     */
    int updateDepotHeadStatusForPurIn(String linkNumber, String number, String type);

    /**
     * 将关联的销售订单单据置为未采购状态-针对销售订单转采购订单的情况
     * 如果linknumber、type和删除订单相同，且有子单据的单据，修改状态为3，不然就是0
     * @param linkNumber
     * @param type
     * @return
     */
    int updateDepotHeadStatusForPurOther(String linkNumber, String type);

    /**
     * 批量修改单据的状态，进行批量审核和反审核
     * @param ids
     * @param status
     * @param token
     * @return
     */
    int batchUpdateStatus(String ids, String status, String token);
}
