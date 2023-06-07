package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.DepotItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meteor.jsherp.domain.extand.DepotItemExMaterialAndDepot;
import com.meteor.jsherp.domain.extand.DepotItemExStock;
import org.apache.ibatis.annotations.MapKey;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_depot_item(单据子表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:54
* @Entity generator.domain.DepotItem
*/
public interface DepotItemMapper extends BaseMapper<DepotItem> {

    BigDecimal getStockCheckSumByDepotList(List<Long> depotIdList, Long id, String stockApprovalFlag, String beginTime, String endTime);

    DepotItemExStock getStockByParamWithDepotList(List<Long> depotIdList, Long mId, String stockApprovalFlag, String beginTime, String endTime);

    DepotItemExStock getSkuStockByParamWithDepotList(List<Long> depotIdList, Long meId, String stockApprovalFlag, String beginTime, String endTime);

    DepotItem getDepotItemByLinkNumberAndMeIdAndId(String linkNumber, Long materialExtendId, Long linkId);

    BigDecimal getRealFinishNumber(String subType, Long materialExtendId, Long linkId, Long currentHeaderId, String linkNumber);

    DepotItem getReturnDepotItemByBatchNumber(String batchNumber);

    @MapKey("meId")
    Map<Long, BigDecimal> getLinkBillDetailMaterialSum(String linkNumber);
    @MapKey("meId")
    Map<Long, BigDecimal> getBatchBillDetailMaterialSum(String linkNumber, String subType);

    List<DepotItemExMaterialAndDepot> getDepotItemMaterialAndDepotByHId(Long headerId);

    List<DepotItem> getListByHidAndHasSerialNumber(Long headerId, String enableSerialNumberEnabled);

    @MapKey("meId")
    Map<Long, BigDecimal> getDepotItemMaterialSum(String linkNumber, String type);
}




