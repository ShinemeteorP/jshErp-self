package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.ImmutableMap;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.controller.SystemConfigController;
import com.meteor.jsherp.domain.*;
import com.meteor.jsherp.domain.extand.DepotItemExMaterialAndDepot;
import com.meteor.jsherp.domain.extand.DepotItemExStock;
import com.meteor.jsherp.domain.extand.MaterialExUnit;
import com.meteor.jsherp.mapper.DepotItemMapper;
import com.meteor.jsherp.service.*;
import com.meteor.jsherp.utils.CommonUtil;
import com.meteor.jsherp.utils.StringUtil;
import com.sun.org.apache.bcel.internal.ExceptionConstants;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @author 刘鑫
* @description 针对表【jsh_depot_item(单据子表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class DepotItemServiceImpl extends CommonServiceImpl<DepotItemMapper, DepotItem>
    implements DepotItemService {

    @Resource
    private DepotHeadService depotHeadService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private OrgaUserRelService orgaUserRelService;

    @Resource
    private DepotItemMapper depotItemMapper;

    @Resource
    private MaterialInitialStockService materialInitialStockService;

    @Resource
    private DepotService depotService;

    @Resource
    private SystemConfigController systemConfigController;

    @Resource
    private UnitService unitService;

    @Resource
    private MaterialCurrentStockService materialCurrentStockService;

    @Resource
    private MaterialExtendService materialExtendService;

    @Resource
    private MaterialService materialService;

    @Resource
    private SerialNumberService serialNumberService;

    @Override
    public Map<String, Object> getMonthsStatics(String token, String type, String approvalFlag) throws Exception{
        User user = userService.getLoginUser(token);
        HashMap<String, Object> map = new HashMap<>();
        String priceLimit = roleService.getRoleByUserId(user.getId()).getPriceLimit();
        long[] userIdList = orgaUserRelService.getUserIdListByRole(user.getId(), type);
        List<String> lastMonths = CommonUtil.getLastMonths(6);
        JSONArray buyPriceList = new JSONArray();
        JSONArray salePriceList = new JSONArray();
        JSONArray retailPriceList = new JSONArray();
        for (String month:
             lastMonths) {
            String beginTime = CommonUtil.firstDayOfMonth(month) + BusinessConstant.DAY_FIRST_TIME;
            String endTime = CommonUtil.lastDayOfMonth(month) + BusinessConstant.DAY_LAST_TIME;
            BigDecimal buyInput = depotHeadService.getBuyInput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal buyOutput = depotHeadService.getBuyOutput(0, beginTime, endTime, userIdList, approvalFlag);
            JSONObject buyObject = new JSONObject();
            BigDecimal buy = buyInput.subtract(buyOutput);
            buyObject.put("x", month);
            buyObject.put("y",
                    BusinessConstant.SHIELD_BUY_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : buy);
            buyPriceList.add(buyObject);

            BigDecimal saleInput = depotHeadService.getSaleInput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal saleOutput = depotHeadService.getSaleOutput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal sale = saleOutput.subtract(saleInput);
            JSONObject saleObject = new JSONObject();
            saleObject.put("x", month);
            saleObject.put("y",
                    BusinessConstant.SHIELD_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : sale);
            salePriceList.add(saleObject);


            BigDecimal retailSaleInput = depotHeadService.getRetailSaleInput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal retailSaleOutput = depotHeadService.getRetailSaleOutput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal retail = retailSaleOutput.subtract(retailSaleInput);
            JSONObject retailObject = new JSONObject();
            retailObject.put("x", month);
            retailObject.put("y",
                    BusinessConstant.SHIELD_RETAIL_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : retail);
            retailPriceList.add(retailObject);
        }
        map.put("buyPriceList", buyPriceList);
        map.put("salePriceList", salePriceList);
        map.put("retailPriceList", retailPriceList);

        return map;
    }

    @Override
    public Map<Long, BigDecimal> getMaterialCountListMap(List<Long> idList) {
        QueryWrapper<DepotItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("header_id", idList);
        List<DepotItem> depotItems = depotItemMapper.selectList(queryWrapper);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (DepotItem d:
             depotItems) {
            sum = sum.add(d.getOperNumber() != null ? d.getOperNumber() : BigDecimal.valueOf(0));
        }
        HashMap<Long, BigDecimal> hashMap = new HashMap<>();
        for (DepotItem d:
            depotItems ) {
            hashMap.put(d.getHeaderId(), sum);
        }
        return hashMap;
    }

    @Override
    public List<Long> getDeoptIdList(Long depotId, String token, String depotFlag){
        List<Long> depotIdList = new ArrayList<>();
        if(depotId == null){
            User user = userService.getLoginUser(token);
            depotIdList = depotService.getDepotIds(user.getId(), depotFlag);
        }else{
            depotIdList.add(depotId);
        }
        return depotIdList;
    }

    @Override
    public BigDecimal getStock(Long depotId, MaterialExUnit material, String token) {
        BigDecimal stock;

        SystemConfig systemConfig = systemConfigController.getCurrent();
        List<Long> depotIdList = getDeoptIdList(depotId, token, systemConfig.getDepotFlag());
        String stockApprovalFlag = systemConfig.getStockApprovalFlag();
        if(StringUtils.hasText(material.getSku())){
            stock = getSkuStockByParam(depotIdList, material.getMeId(), stockApprovalFlag, null, null);
        }else{
            stock = getStockByParam(depotIdList, material.getId(), stockApprovalFlag, null, null);
            if(material.getUnitId() != null){
                Unit unit = unitService.getById(material.getUnitId());
                stock = unitService.parseStockByUnit(unit, stock, material.getCommodityUnit());
            }

        }
        return stock;
    }

    @Override
    public BigDecimal getSkuStockByParam(List<Long> depotIdList, Long meId, String stockApprovalFlag, String beginTime, String endTime) {
        DepotItemExStock depotItemExStock = depotItemMapper.getSkuStockByParamWithDepotList(depotIdList, meId, stockApprovalFlag,  beginTime, endTime);
        BigDecimal stockSum = BigDecimal.ZERO;
        if(depotItemExStock != null){
            BigDecimal inTotal = depotItemExStock.getInTotal();
            BigDecimal transfInTotal = depotItemExStock.getTransfInTotal();
            BigDecimal assemInTotal = depotItemExStock.getAssemInTotal();
            BigDecimal disAssemInTotal = depotItemExStock.getDisAssemInTotal();
            BigDecimal outTotal = depotItemExStock.getOutTotal();
            BigDecimal transfOutTotal = depotItemExStock.getTransfOutTotal();
            BigDecimal assemOutTotal = depotItemExStock.getAssemOutTotal();
            BigDecimal disAssemOutTotal = depotItemExStock.getDisAssemOutTotal();
            stockSum = inTotal.add(transfInTotal).add(assemInTotal).add(disAssemInTotal)
                    .subtract(outTotal).subtract(transfOutTotal).subtract(assemOutTotal).subtract(disAssemOutTotal);
        }
        return stockSum;
    }

    @Override
    public BigDecimal getStockByParam(List<Long> depotIdList, Long mid, String stockApprovalFlag, String beginTime, String endTime){
        //获取初始库存
        BigDecimal initialStock = materialInitialStockService.getInitStockByDepotAndMaterial(depotIdList, mid);
        //获取盘点后的库存数量变化
        BigDecimal stockCheckSum = depotItemMapper.getStockCheckSumByDepotList(depotIdList, mid, stockApprovalFlag, beginTime, endTime);
        DepotItemExStock depotItemExStock = depotItemMapper.getStockByParamWithDepotList(depotIdList, mid, stockApprovalFlag,  beginTime, endTime);
        BigDecimal stockSum = BigDecimal.ZERO;
        if(depotItemExStock != null){
            BigDecimal inTotal = depotItemExStock.getInTotal();
            BigDecimal transfInTotal = depotItemExStock.getTransfInTotal();
            BigDecimal assemInTotal = depotItemExStock.getAssemInTotal();
            BigDecimal disAssemInTotal = depotItemExStock.getDisAssemInTotal();
            BigDecimal outTotal = depotItemExStock.getOutTotal();
            BigDecimal transfOutTotal = depotItemExStock.getTransfOutTotal();
            BigDecimal assemOutTotal = depotItemExStock.getAssemOutTotal();
            BigDecimal disAssemOutTotal = depotItemExStock.getDisAssemOutTotal();
            stockSum = inTotal.add(transfInTotal).add(assemInTotal).add(disAssemInTotal)
                    .subtract(outTotal).subtract(transfOutTotal).subtract(assemOutTotal).subtract(disAssemOutTotal);
        }
        return initialStock.add(stockCheckSum).add(stockSum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDetail(String rows, DepotHead depotHead, String actionType, String token) {
        //删除序列号和回收序列号
        deleteOrCancelSerialNumber(actionType, depotHead, token);
        User user = userService.getLoginUser(token);
        //删除单据的明细
        deleteDepotItemHeadId(depotHead.getId(), token);

        JSONArray array = JSONArray.parseArray(rows);
        if(array != null && array.size() > 0){
            //针对组装单、拆卸单校验是否存在组合件和普通子件
            checkAssembleWithMaterialType(array, depotHead.getSubType());
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = JSONObject.parseObject(array.getString(i));
                DepotItem depotItem = new DepotItem();
                depotItem.setHeaderId(depotHead.getId());
                String barCode = obj.getString("barCode");
                MaterialExtend materialExtend = materialExtendService.getOneByKey("bar_code", barCode);
                if(materialExtend == null){
                    materialExtend = new MaterialExtend();
                }
                depotItem.setMaterialId(materialExtend.getMaterialId());
                depotItem.setMaterialExtendId(materialExtend.getId());
                depotItem.setMaterialUnit(obj.getString("unit"));
                Material material = materialService.getById(depotItem.getMaterialId());
                if(material == null){
                    material = new Material();
                }
                //商品的序列号标识或者批号标识打开了，判断订单的类型，部分类型订单不能开启序列号标识和批号标识
                if(BusinessConstant.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber()) ||
                        BusinessConstant.ENABLE_BATCH_NUMBER_ENABLED.equals(material.getEnableBatchNumber())){
                    //组装拆卸单不能选择批号或序列号商品
                    if(BusinessConstant.SUB_TYPE_ASSEMBLE.equals(depotHead.getSubType()) ||
                            BusinessConstant.SUB_TYPE_DISASSEMBLE.equals(depotHead.getSubType())) {
                        throw new RuntimeException(ExceptionConstant.MATERIAL_ASSEMBLE_SELECT_ERROR_MSG);
                    }
                    //调拨单不能选择批号或序列号商品（该场景走出库和入库单）
                    if(BusinessConstant.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())) {
                        throw new RuntimeException(ExceptionConstant.MATERIAL_TRANSFER_SELECT_ERROR_MSG);
                    }
                    //盘点业务不能选择批号或序列号商品（该场景走出库和入库单）
                    if(BusinessConstant.SUB_TYPE_CHECK_ENTER.equals(depotHead.getSubType())
                            ||BusinessConstant.SUB_TYPE_REPLAY.equals(depotHead.getSubType())) {
                        throw new RuntimeException(ExceptionConstant.MATERIAL_STOCK_CHECK_ERROR_MSG);
                    }
                }
                if(StringUtils.hasText(obj.getString("snList"))){
                    depotItem.setSnList(obj.getString("snList"));
                    if(StringUtils.hasText(obj.getString("depotId"))){
                        String[] snArray = depotItem.getSnList().split(",");
                        Integer operNumber = obj.getInteger("operNumber");
                        if(operNumber == snArray.length){
                            Long depotId = obj.getLong("depotId");
                            //录入序列号
                            serialNumberService.addSerialNumberByBill(depotHead.getType(), depotHead.getSubType(),
                                    depotHead.getNumber(), materialExtend.getMaterialId(), depotItem.getDepotId(), depotItem.getSnList(), user.getId());
                        }else{
                            throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_SN_NUMBER_FAILED_MSG);
                        }
                    }
                }else{
                    if("入库".equals(depotHead.getType()) || "出库".equals(depotHead.getType())){
                        if(BusinessConstant.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())){
                            throw new RuntimeException(ExceptionConstant.MATERIAL_SERIAL_NUMBER_EMPTY_MSG);
                        }
                    }
                }
                if(StringUtils.hasText(obj.getString("batchNumber"))){
                    depotItem.setBatchNumber(obj.getString("batchNumber"));
                }else {
                    if (BusinessConstant.ENABLE_BATCH_NUMBER_ENABLED.equals(material.getEnableBatchNumber())) {
                        if ("入库".equals(depotHead.getType()) || "出库".equals(depotHead.getType())) {
                            throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_BATCH_NUMBER_EMPTY_MSG);
                        }
                    }
                }
                if (StringUtil.isExist(obj.get("expirationDate"))) {
                    depotItem.setExpirationDate(obj.getDate("expirationDate"));
                }
                if (StringUtil.isExist(obj.get("sku"))) {
                    depotItem.setSku(obj.getString("sku"));
                }
                if (StringUtil.isExist(obj.get("linkId"))) {
                    depotItem.setLinkId(obj.getLong("linkId"));
                }
                //进行单位换算
                Unit unit = materialService.getUnitByMaterialId(materialExtend.getMaterialId());
                if(StringUtils.hasText(obj.getString("operNumber"))){
                    depotItem.setOperNumber(obj.getBigDecimal("operNumber"));
                    String unitStr = obj.getString("unit");
                    if(StringUtils.hasText(unit.getName())){
                        if(unitStr.equals(unit.getBasicUnit())){
                            depotItem.setBasicNumber(depotItem.getOperNumber());
                        }else if(unitStr.equals(unit.getOtherUnit())){
                            depotItem.setBasicNumber(depotItem.getOperNumber().multiply(unit.getRatio()));
                        }else if(unitStr.equals(unit.getOtherUnitTwo())){
                            depotItem.setBasicNumber(depotItem.getOperNumber().multiply(unit.getRatioTwo()));
                        }else if(unitStr.equals(unit.getOtherUnitThree())){
                            depotItem.setBasicNumber(depotItem.getOperNumber().multiply(unit.getRatioThree()));
                        }
                    }else{
                        depotItem.setBasicNumber(depotItem.getOperNumber());
                    }
                }
                //如果数量+已完成数量>原订单数量，给出预警(判断前提是存在关联订单)
                if(StringUtils.hasText(depotHead.getLinkNumber())
                        && StringUtils.hasText(obj.getString("preNumber")) && StringUtils.hasText(obj.getString("finishNumber"))){
                    if("add".equals(actionType)){
                        //在新增模式下赋值
                        BigDecimal preNumber = obj.getBigDecimal("preNumber");
                        BigDecimal finishNumber = obj.getBigDecimal("finishNumber");
                        if(depotItem.getOperNumber().add(finishNumber).compareTo(preNumber) > 0){
                            throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_MSG);
                        }
                    }else if("update".equals(actionType)){
                        //在更新模式进行状态赋值
                        String unitStr = obj.getString("unit");
                        Long preHeaderId = depotHeadService.getOneByKey("number", depotHead.getLinkNumber()).getId();
                        //前一个单据的数量
                        BigDecimal preNumber = depotItemMapper.getDepotItemByLinkNumberAndMeIdAndId(depotHead.getLinkNumber(), depotItem.getMaterialExtendId(), depotItem.getLinkId()).getOperNumber();
                        //除去此单据之外的已入库|已出库单据数量
                        BigDecimal realFinishNumber = getRealFinishNumber(depotHead.getSubType(),  depotItem.getMaterialExtendId(), depotItem.getLinkId(), preHeaderId, depotHead.getId(), unit, unitStr);
                        if(depotItem.getOperNumber().add(realFinishNumber).compareTo(preNumber) > 0){
                            throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_MSG);
                        }
                    }

                }
                if (StringUtils.hasText(obj.getString("unitPrice"))){
                    depotItem.setUnitPrice(obj.getBigDecimal("unitPrice"));
                    if (materialExtend.getLowDecimal() != null && depotItem.getUnitPrice().compareTo(materialExtend.getLowDecimal()) < 0){
                        throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_UNIT_PRICE_LOW_MSG);
                    }
                }
                //如果是销售出库、销售退货、零售出库、零售退货则给采购单价字段赋值（如果是批次商品，则要根据批号去找之前的入库价），否则是商品的采购价
                if(BusinessConstant.SUB_TYPE_SALES.equals(depotHead.getSubType())
                        || BusinessConstant.SUB_TYPE_SALES_RETURN.equals(depotHead.getSubType())
                        || BusinessConstant.SUB_TYPE_RETAIL.equals(depotHead.getSubType())
                        || BusinessConstant.SUB_TYPE_RETAIL_RETURN.equals(depotHead.getSubType())){
                    if(StringUtils.hasText(depotItem.getBatchNumber())){
                        //根据批号以及单据类型是入库的子单据
                        depotItem.setPurchaseUnitPrice(depotItemMapper.getReturnDepotItemByBatchNumber(depotItem.getBatchNumber()).getUnitPrice());
                    }else{
                        depotItem.setPurchaseUnitPrice(materialExtend.getPurchaseDecimal());
                    }
                }
                if (StringUtil.isExist(obj.get("taxUnitPrice"))) {
                    depotItem.setTaxUnitPrice(obj.getBigDecimal("taxUnitPrice"));
                }
                if (StringUtil.isExist(obj.get("allPrice"))) {
                    depotItem.setAllPrice(obj.getBigDecimal("allPrice"));
                }
                if (StringUtil.isExist(obj.get("depotId"))) {
                    depotItem.setDepotId(obj.getLong("depotId"));
                }else {
                    //只要不是销售订单和采购订单，仓库为空就报错
                    if (!BusinessConstant.SUB_TYPE_PURCHASE_ORDER.equals(depotHead.getSubType())
                            && !BusinessConstant.SUB_TYPE_SALES_ORDER.equals(depotHead.getSubType())) {
                        throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_DEPOT_FAILED_MSG);
                    }

                }
                //如果是调拨订单，子单据的调入仓库（another）不能为空，且与调出仓库不能相同
                if(BusinessConstant.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())){
                    if(StringUtils.hasText(obj.getString("anotherDepotId"))){
                        if (obj.getLong("anotherDepotId").equals(obj.getLong("depotId"))){
                            throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_ANOTHER_DEPOT_EQUAL_FAILED_MSG);
                        }else{
                            depotItem.setAnotherDepotId(obj.getLong("anotherDepotId"));
                        }
                    }else{
                        throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_ANOTHER_DEPOT_FAILED_MSG);
                    }
                }
                if (StringUtil.isExist(obj.get("taxRate"))) {
                    depotItem.setTaxRate(obj.getBigDecimal("taxRate"));
                }
                if (StringUtil.isExist(obj.get("taxMoney"))) {
                    depotItem.setTaxMoney(obj.getBigDecimal("taxMoney"));
                }
                if (StringUtil.isExist(obj.get("taxLastMoney"))) {
                    depotItem.setTaxLastMoney(obj.getBigDecimal("taxLastMoney"));
                }
                if (StringUtil.isExist(obj.get("mType"))) {
                    depotItem.setMaterialType(obj.getString("mType"));
                }
                if (StringUtil.isExist(obj.get("remark"))) {
                    depotItem.setRemark(obj.getString("remark"));
                }
                //出库订单判断库存是否充足
                if("出库".equals(depotHead.getType())){
                    BigDecimal stock = getStock(depotItem.getDepotId(), new MaterialExUnit(depotItem), token);
                    BigDecimal thisBasicNumber = depotItem.getBasicNumber() == null ? BigDecimal.ZERO : depotItem.getBasicNumber();
                    if(!"1".equals(systemConfigController.getCurrent().getMinusStockFlag()) && stock.compareTo(thisBasicNumber) < 0){
                        throw new RuntimeException(ExceptionConstant.MATERIAL_STOCK_NOT_ENOUGH_MSG);
                    }
                    //出库订单处理序列号，判断商品是否开启序列号，开启的售出序列号，未开启的跳过
                    if(!BusinessConstant.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())
                            && BusinessConstant.ENABLE_SERIAL_NUMBER_ENABLED.equals(material.getEnableSerialNumber())){
                        //售出序列号，获得当前操作人
                        serialNumberService.checkAndUpdateSerialNumber(depotItem, depotHead.getNumber(), user, StringUtil.toNull(depotItem.getSnList()));
                    }
                }
                depotItemMapper.insert(depotItem);
                //插入单据后，更新物料库存
                updateStockByDepotItem(depotItem, token);
                //更新商品价格
                updateMaterialExtendPrice(materialExtend.getId(), depotHead.getSubType(), obj, user);
                //如果关联单据号非空则更新订单的状态,单据类型：采购入库单或销售出库单或盘点复盘单
                String billStatus = getBillStatusByParam(depotHead);
                if(BusinessConstant.SUB_TYPE_PURCHASE.equals(depotHead.getSubType())
                        || BusinessConstant.SUB_TYPE_SALES.equals(depotHead.getSubType())
                        || BusinessConstant.SUB_TYPE_REPLAY.equals(depotHead.getSubType())) {
                    if(StringUtil.isNotEmpty(depotHead.getLinkNumber())) {
                        //单据状态:是否全部完成 2-全部完成 3-部分完成（针对订单的分批出入库）

                        depotHeadService.update(new UpdateWrapper<DepotHead>().set("status", billStatus).eq("number", depotHead.getLinkNumber()));
                    }
                }
                //如果关联单据号非空则更新订单的状态,此处针对销售订单转采购订单的场景
                if(BusinessConstant.SUB_TYPE_PURCHASE_ORDER.equals(depotHead.getSubType())) {
                    if(StringUtil.isNotEmpty(depotHead.getLinkNumber())) {
                        depotHeadService.update(new UpdateWrapper<DepotHead>().set("purchase_status", billStatus).eq("number", depotHead.getLinkNumber()));
                    }
                }
            }

        }else{
            throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_ROW_FAILED_MSG);
        }
    }

    /**
     * 通过数组对比：原单据的商品和商品数量（汇总） 与 分批操作后单据的商品和商品数量（汇总）
     * 获取单据状态  billsStatus '0'未审核 '1'审核 '2'完成采购|销售 '3'部分采购|销售
     * @param depotHead
     * @return
     */
    @Override
    public String getBillStatusByParam(DepotHead depotHead) {
        //获取原单据的商品和商品数量（汇总）
        Map<Long, BigDecimal> originalNumber = depotItemMapper.getLinkBillDetailMaterialSum(depotHead.getLinkNumber());
        //获取分批操作后单据的商品和商品数量（汇总）
        Map<Long, BigDecimal> batchNumber = depotItemMapper.getBatchBillDetailMaterialSum(depotHead.getLinkNumber(), depotHead.getSubType());
        Set<Map.Entry<Long, BigDecimal>> entries = batchNumber.entrySet();
        for (Map.Entry<Long, BigDecimal> e:
             entries) {
            BigDecimal bigDecimal = originalNumber.get(e.getKey());
            if (bigDecimal.compareTo(BigDecimal.ZERO) != 0) {
                if(bigDecimal == null || (bigDecimal != null && bigDecimal.compareTo(e.getValue()) != 0)){
                    return BusinessConstant.BILLS_STATUS_SKIPING;
                }
            }
        }
        return BusinessConstant.BILLS_STATUS_SKIPED;
    }

    /**
     *  根据单据的子类，更新商品不同的价格
     * @param id 商品扩展的id
     * @param subType
     * @param obj
     * @param user
     * @return
     */
    @Override
    public boolean updateMaterialExtendPrice(Long id, String subType, JSONObject obj, User user) {
        if(StringUtils.hasText(obj.getString("unitPrice"))){
            MaterialExtend materialExtend = new MaterialExtend();
            BigDecimal unitPrice = obj.getBigDecimal("unitPrice");
            materialExtend.setId(id);
            if(BusinessConstant.SUB_TYPE_PURCHASE.equals(subType)) {
                materialExtend.setPurchaseDecimal(unitPrice);
            }
            if(BusinessConstant.SUB_TYPE_SALES.equals(subType)) {
                materialExtend.setWholesaleDecimal(unitPrice);
            }
            if(BusinessConstant.SUB_TYPE_RETAIL.equals(subType)) {
                materialExtend.setCommodityDecimal(unitPrice);
            }
            materialExtend.setUpdateSerial(user.getLoginName());
            materialExtend.setUpdateTime(System.currentTimeMillis());
            return materialExtendService.updateById(materialExtend);
        }
        return false;
    }

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
    @Override
    public BigDecimal getRealFinishNumber(String subType, Long materialExtendId, Long linkId, Long preHeaderId, Long currentHeaderId, Unit unit, String unitStr) {
        DepotHead depotHead = depotHeadService.selectOne(preHeaderId);
        String linkNumber = depotHead.getNumber();
        BigDecimal realFinishNumber = depotItemMapper.getRealFinishNumber(subType, materialExtendId, linkId, currentHeaderId, linkNumber);
        if(StringUtils.hasText(unitStr)){
            realFinishNumber = parseNumberUnit(realFinishNumber, unit, unitStr);
        }
        return realFinishNumber;
    }

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
    @Override
    public BigDecimal getRealFinishNumberWithLinkType(Long materialExtendId, Long linkId, Long preHeaderId, Long currentHeaderId, Unit unit, String unitStr, String linkType){
        DepotHead depotHead = depotHeadService.selectOne(preHeaderId);
        String linkNumber = depotHead.getNumber();
        String subType = "";
        if("purchase".equals(linkType)) {
            //针对以销定购的情况
            if(BusinessConstant.SUB_TYPE_SALES_ORDER.equals(depotHead.getSubType())){
                subType = BusinessConstant.SUB_TYPE_PURCHASE_ORDER;
            }
        }else {
            //采购订单转采购入库
            if(BusinessConstant.SUB_TYPE_PURCHASE_ORDER.equals(depotHead.getSubType())) {
                subType = BusinessConstant.SUB_TYPE_PURCHASE;
            }
            //销售订单转销售出库
            if(BusinessConstant.SUB_TYPE_SALES_ORDER.equals(depotHead.getSubType())) {
                subType = BusinessConstant.SUB_TYPE_SALES;
            }
            //采购入库转采购退货
            if(BusinessConstant.SUB_TYPE_PURCHASE.equals(depotHead.getSubType())) {
                subType = BusinessConstant.SUB_TYPE_PURCHASE_RETURN;
            }
            //销售出库转销售退货
            if(BusinessConstant.SUB_TYPE_SALES.equals(depotHead.getSubType())) {
                subType = BusinessConstant.SUB_TYPE_SALES_RETURN;
            }
        }
        BigDecimal realFinishNumber = depotItemMapper.getRealFinishNumber(subType, materialExtendId, linkId, currentHeaderId, linkNumber);
        if(StringUtils.hasText(unitStr)){
            realFinishNumber = parseNumberUnit(realFinishNumber, unit, unitStr);
        }
        return realFinishNumber;
    }

    /**
     * 私有方法，根据需要转换单位和目前单位转换数量
     * @param finishNumber 需要转换的数量
     * @param unit 转换后的单位
     * @param unitStr  转换前的单位描述
     * @return
     */
    private BigDecimal parseNumberUnit(BigDecimal finishNumber, Unit unit, String unitStr){
        if(unitStr.equals(unit.getOtherUnit()) && unit.getRatio() != null && unit.getRatio().compareTo(BigDecimal.ZERO) != 0){
            return finishNumber.divide(unit.getRatio(), 2, BigDecimal.ROUND_HALF_UP);
        }else if(unitStr.equals(unit.getOtherUnitTwo()) && unit.getRatioTwo() != null && unit.getRatioTwo().compareTo(BigDecimal.ZERO) != 0){
            return finishNumber.divide(unit.getRatioTwo(), 2, BigDecimal.ROUND_HALF_UP);
        }else if(unitStr.equals(unit.getOtherUnitThree()) && unit.getRatioThree() != null && unit.getRatioThree().compareTo(BigDecimal.ZERO) != 0){
            return finishNumber.divide(unit.getRatioThree(), 2, BigDecimal.ROUND_HALF_UP);
        }
        return finishNumber;
    }

    /**
     * 检查组装单和拆线单是否同时有组合件和普通子件
     * @param array
     * @param subType
     */
    @Override
    public void checkAssembleWithMaterialType(JSONArray array, String subType) {
        if(BusinessConstant.SUB_TYPE_ASSEMBLE.equals(subType) || BusinessConstant.SUB_TYPE_DISASSEMBLE.equals(subType)){
            if(array.size() > 1){
                JSONObject first = JSONObject.parseObject(array.getString(0));
                JSONObject second = JSONObject.parseObject(array.getString(1));
                String firstType = first.getString("mType");
                String secondType = second.getString("mType");
                if(!"组合件".equals(firstType) || !"普通子件".equals(secondType)){
                    throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_CHECK_ASSEMBLE_EMPTY_MSG);
                }
            }else{

                throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_CHECK_ASSEMBLE_EMPTY_MSG);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepotItemHeadId(Long headerId, String token) {
        List<DepotItem> depotItemList = getListByKey("header_id", headerId);
        remove(new QueryWrapper<DepotItem>().eq("header_id", headerId));
        for (DepotItem d:
             depotItemList) {
            updateStockByDepotItem(d, token);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrCancelSerialNumber(String actionType, DepotHead depotHead, String token) {
        if("update".equals(actionType)){

            if("入库".equals(depotHead.getType())){
                String number = depotHead.getNumber();
                serialNumberService.deleteByInBillNo(number);
            }else if ("出库".equals(depotHead.getType())){
                User user = userService.getLoginUser(token);
                List<DepotItem> depotItems = depotItemMapper.selectList(new QueryWrapper<DepotItem>().eq("header_id", depotHead.getId()));
                if(depotItems != null){
                    for (DepotItem d:
                         depotItems) {
                        if(StringUtils.hasText(d.getSnList())){
                            int size = serialNumberService.updateByOutBillNoAndMId(d.getMaterialId(), depotHead.getNumber(), d.getBasicNumber(), user);
                        }
                    }
                }

            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStockByDepotItem(DepotItem depotItem, String token) {
        updateCurrentStockFun(depotItem.getMaterialId(), depotItem.getDepotId(), token);
        //如果该单据有多个仓库存储了整个物料，再遍历另一个仓库
        if(depotItem.getAnotherDepotId()!=null){
            updateCurrentStockFun(depotItem.getMaterialId(), depotItem.getAnotherDepotId(), token);
        }
    }

    @Override
    public void updateCurrentStockFun(Long materialId, Long depotId, String token) {
        List<MaterialCurrentStock> currentStocks = materialCurrentStockService.getListByKeyMap
                (ImmutableMap.of("material_id", materialId, "depot_id", depotId));
        MaterialCurrentStock currentStock = new MaterialCurrentStock();
        currentStock.setMaterialId(materialId);
        currentStock.setDepotId(depotId);
        SystemConfig systemConfig = systemConfigController.getCurrent();
        List<Long> depotIdList = getDeoptIdList(depotId, token, systemConfig.getDepotFlag());
        String stockApprovalFlag = systemConfig.getStockApprovalFlag();
        currentStock.setCurrentNumber(getStockByParam(depotIdList,materialId, stockApprovalFlag, null, null ));
        if(currentStocks !=null && currentStocks.size()>0) {
            Long mcsId = currentStocks.get(0).getId();
            currentStock.setId(mcsId);
            materialCurrentStockService.update(currentStock, new QueryWrapper<MaterialCurrentStock>()
                    .eq("material_id", materialId).eq("depot_id", depotId));
        } else {
            materialCurrentStockService.save(currentStock);
        }
    }

    @Override
    public JSONObject getDetailList(Long headerId, String mpList, String linkType, String isReadOnly, String token) {
        JSONObject data = new JSONObject();
        List<DepotItemExMaterialAndDepot> list = null;
        if(headerId != null){
            list = depotItemMapper.getDepotItemMaterialAndDepotByHId(headerId);
        }
        data.put("total", list.size());
        JSONArray array = new JSONArray();
        String[] mpArr = mpList.split(",");
        if(list != null){
            BigDecimal totalAllPrice = BigDecimal.ZERO;
            BigDecimal totalOperNumber = BigDecimal.ZERO;
            BigDecimal totalTaxMoney = BigDecimal.ZERO;
            BigDecimal totalTaxLastMoney = BigDecimal.ZERO;
            BigDecimal totalWeight = BigDecimal.ZERO;
            for (DepotItemExMaterialAndDepot depotItem:
                 list) {
                JSONObject obj = new JSONObject();
                obj.put("id", depotItem.getId());
                obj.put("materialExtendId", depotItem.getMaterialExtendId() == null ? "" : depotItem.getMaterialExtendId());
                obj.put("barCode", depotItem.getBarCode());
                obj.put("name", depotItem.getMName());
                obj.put("standard", depotItem.getMStandard());
                obj.put("model", depotItem.getMModel());
                obj.put("color", depotItem.getMColor());
                obj.put("materialOther", getMaterialOtherByParam(mpArr, depotItem));
                Unit unit = unitService.getUnitByMId(depotItem.getMaterialId());//获取单位信息
                BigDecimal stock = getStock(depotItem.getDepotId(),
                        new MaterialExUnit(depotItem.getSku(), depotItem.getMaterialId(),
                                depotItem.getMaterialExtendId(), unit.getId(), depotItem.getMaterialUnit()), token);
                obj.put("stock", stock);
                obj.put("unit", depotItem.getMaterialUnit());
                obj.put("snList", depotItem.getSnList());
                obj.put("batchNumber", depotItem.getBatchNumber());
                obj.put("expirationDate", CommonUtil.parseDateToStr(depotItem.getExpirationDate()));
                obj.put("sku", depotItem.getSku());
                obj.put("enableSerialNumber", depotItem.getEnableSerialNumber());
                obj.put("enableBatchNumber", depotItem.getEnableBatchNumber());
                obj.put("operNumber", depotItem.getOperNumber());
                obj.put("basicNumber", depotItem.getBasicNumber());
                obj.put("preNumber", depotItem.getOperNumber()); //原数量
                obj.put("finishNumber", getRealFinishNumberWithLinkType(depotItem.getMaterialExtendId(), depotItem.getId(), depotItem.getHeaderId(), depotItem.getHeaderId(), unit, depotItem.getMaterialUnit(), linkType));
                obj.put("purchaseDecimal", depotItem.getPurchaseDecimal());
                if ("basic".equals(linkType)){
                    //只有正常情况下才能显示价格金额，以销定购不能显示
                    obj.put("unitPrice", depotItem.getUnitPrice());
                    obj.put("taxUnitPrice", depotItem.getTaxUnitPrice());
                    obj.put("allPrice", depotItem.getAllPrice());
                    obj.put("taxRate", depotItem.getTaxRate());
                    obj.put("taxMoney", depotItem.getTaxMoney());
                    obj.put("taxLastMoney", depotItem.getTaxLastMoney());
                }
                BigDecimal weight = depotItem.getBasicNumber() == null || depotItem.getWeight() == null ?
                        BigDecimal.ZERO : depotItem.getBasicNumber().multiply(depotItem.getWeight());
                obj.put("weight", weight);
                obj.put("remark", depotItem.getRemark());
                obj.put("linkId", depotItem.getLinkId());
                obj.put("depotId", depotItem.getDepotId() == null ? "" : depotItem.getDepotId());
                obj.put("depotName", depotItem.getDepotId() == null ? "" : depotItem.getDepotName());
                obj.put("anotherDepotId", depotItem.getAnotherDepotId() == null ? "" : depotItem.getAnotherDepotId());
                obj.put("anotherDepotName", depotItem.getAnotherDepotId() == null ? "" : depotItem.getAnotherDepotName());
                obj.put("mType", depotItem.getMaterialType());
                obj.put("op", 1);
                array.add(obj);
                if(depotItem.getOperNumber() != null){
                    totalOperNumber.add(depotItem.getOperNumber());
                }
                if(depotItem.getAllPrice() != null){
                    totalAllPrice.add(depotItem.getAllPrice());
                }
                if (depotItem.getTaxLastMoney() != null) {
                    totalTaxLastMoney.add(depotItem.getTaxLastMoney());
                }
                if (depotItem.getTaxMoney() != null) {
                    totalTaxMoney.add(depotItem.getTaxMoney());
                }
                totalWeight.add(weight);
            }
            if("1".equals(isReadOnly)){
                JSONObject footItem = new JSONObject();
                footItem.put("operNumber", totalOperNumber);
                footItem.put("taxMonet", totalTaxMoney);
                footItem.put("taxLastMoney", totalTaxLastMoney);
                footItem.put("allPrice", totalAllPrice);
                footItem.put("weight", totalWeight);
                array.add(footItem);
            }
        }
        data.put("rows", array);
        return data;
    }

    @Override
    public List<DepotItem> getDepotItemListByHidHasSNumber(Long headerId, String enableSerialNumberEnabled) {
        List<DepotItem> depotItems = depotItemMapper.getListByHidAndHasSerialNumber(headerId, enableSerialNumberEnabled);
        return depotItems == null ? new ArrayList<DepotItem>() : depotItems;
    }

    @Override
    public Map<Long, BigDecimal> getDepotItemMaterialSum(String linkNumber, String type) {
        return depotItemMapper.getDepotItemMaterialSum(linkNumber, type);
    }

    public String getMaterialOtherByParam(String[] mpArr, DepotItemExMaterialAndDepot depotItem) {
        String materialOther = "";
        for (int i = 0; i < mpArr.length; i++) {
            if (mpArr[i].equals("制造商")) {
                materialOther = materialOther + ((depotItem.getMMfrs() == null || depotItem.getMMfrs().equals("")) ? "" : "(" + depotItem.getMMfrs() + ")");
            }
            if (mpArr[i].equals("自定义1")) {
                materialOther = materialOther + ((depotItem.getMOtherField1() == null || depotItem.getMOtherField1().equals("")) ? "" : "(" + depotItem.getMOtherField1() + ")");
            }
            if (mpArr[i].equals("自定义2")) {
                materialOther = materialOther + ((depotItem.getMOtherField2() == null || depotItem.getMOtherField2().equals("")) ? "" : "(" + depotItem.getMOtherField2() + ")");
            }
            if (mpArr[i].equals("自定义3")) {
                materialOther = materialOther + ((depotItem.getMOtherField3() == null || depotItem.getMOtherField3().equals("")) ? "" : "(" + depotItem.getMOtherField3() + ")");
            }
        }
        return materialOther;
    }
}




