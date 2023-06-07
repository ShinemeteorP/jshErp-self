package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.domain.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.extand.DepotItemExMaterialAndDepot;
import com.meteor.jsherp.domain.extand.MaterialExUnit;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 根据depotId 获取对应的仓库id列表，不为空则只有depotId，否则返回用户所拥有权限的仓库id
     * @param depotId
     * @param token
     * @param depotFlag
     * @return
     */
    List<Long> getDeoptIdList(Long depotId, String token, String depotFlag);

    /**
     * 根据仓库和物料获取该物料在该仓库或者登入用户有权限的仓库的库存量
     * @param depotId
     * @param material
     * @return
     */
    BigDecimal getStock(Long depotId, MaterialExUnit material, String token);

    /**
     * 获取对应sku的各种盘点库存
     * @param depotIdList
     * @param meId 物料扩展id
     * @param stockApprovalFlag
     * @param beginTime
     * @param endTime
     * @return
     */
    BigDecimal getSkuStockByParam(List<Long> depotIdList, Long meId, String stockApprovalFlag, String beginTime, String endTime);

    /**
     * 获取物料的盘点后库存
     * @param depotIdList
     * @param mid 物料id
     * @param stockApprovalFlag
     * @param beginTime
     * @param endTime
     * @return
     */
    BigDecimal getStockByParam(List<Long> depotIdList, Long mid, String stockApprovalFlag, String beginTime, String endTime);

    /**
     * 单据存储成功后，再将单据的子单据信息，插入表格中
     * @param rows
     * @param depotHead
     * @param add
     */
    void saveDetail(String rows, DepotHead depotHead, String add, String token);

    /**
     * 通过数组对比：原单据的商品和商品数量（汇总） 与 分批操作后单据的商品和商品数量（汇总）
     * 获取单据状态  billsStatus '0'未审核 '1'审核 '2'完成采购|销售 '3'部分采购|销售
     * @param depotHead
     * @return
     */
    String getBillStatusByParam(DepotHead depotHead);

    /**
     *  根据单据的子类，更新商品不同的价格
     * @param id 商品扩展的id
     * @param subType
     * @param obj
     * @param user
     * @return
     */
    boolean updateMaterialExtendPrice(Long id, String subType, JSONObject obj, User user);

    /**
     * 此单据之外的已入库|已出库单据数量
     * @param subType
     * @param materialExtendId
     * @param linkId
     * @param preHeaderId   此单据的关联单据id
     * @param currentHeaderId  此单据id
     * @param unit
     * @param unitStr
     * @return
     */
    BigDecimal getRealFinishNumber(String subType, Long materialExtendId, Long linkId, Long preHeaderId, Long currentHeaderId, Unit unit, String unitStr);

    /**
     * 根据linkType的情况来获取关联订单的 已入库|已出库单据数量
     * @param materialExtendId
     * @param linkId  子单据关联的子单据id
     * @param preHeaderId
     * @param currentHeaderId
     * @param unit
     * @param unitStr
     * @param linkType  purchase 表示是以销订购的模式
     * @return
     */
    BigDecimal getRealFinishNumberWithLinkType(Long materialExtendId, Long linkId, Long preHeaderId, Long currentHeaderId, Unit unit, String unitStr, String linkType);

    /**
     * 检查组装单和拆线单是否同时有组合件和普通子件
     * @param array
     * @param subType
     */
    void checkAssembleWithMaterialType(JSONArray array, String subType);

    /**
     * 根据header_id删除子单据，并且更新这些删除子单据的物料库存
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteDepotItemHeadId(Long id, String token);

    @Transactional(rollbackFor = Exception.class)
    void deleteOrCancelSerialNumber(String actionType, DepotHead depotHead, String token);

    /**
     * 根据子单据更新物料的库存
     * @param depotItem
     */
    @Transactional(rollbackFor = Exception.class)
    void updateStockByDepotItem(DepotItem depotItem, String token);

    /**
     * 根据物料id和仓库id更新物料库存
     * @param materialId
     * @param anotherDepotId
     */
    void updateCurrentStockFun(Long materialId, Long anotherDepotId, String token);


    /**
     * 通过headerId获取depotItem以及相关的物料和仓库信息
     * @param headerId
     * @return
     */
    JSONObject getDetailList(Long headerId, String mpList, String linkType, String isReadOnly, String token);

    /**
     * 根据headerid获取物料有序列号的子单据列表
     * @param headerId
     * @param enableSerialNumberEnabled
     * @return
     */
    List<DepotItem> getDepotItemListByHidHasSNumber(Long headerId, String enableSerialNumberEnabled);

    /**
     * 根据单据的大类和关联单据，获取子单据中物料id （key） 和 id对应的数量之和的map
     * @param linkNumber
     * @param type
     * @return
     */
    Map<Long, BigDecimal> getDepotItemMaterialSum(String linkNumber, String type);
}
