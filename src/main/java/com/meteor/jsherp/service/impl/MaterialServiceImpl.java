package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.controller.SystemConfigController;
import com.meteor.jsherp.domain.Material;
import com.meteor.jsherp.domain.SystemConfig;
import com.meteor.jsherp.domain.Unit;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.extand.MaterialExUnit;
import com.meteor.jsherp.mapper.MaterialMapper;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_material(产品表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MaterialServiceImpl extends CommonServiceImpl<MaterialMapper, Material>
    implements MaterialService{

    @Resource
    private MaterialCategoryService materialCategoryService;

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private UnitService unitService;

    @Resource
    private DepotItemService depotItemService;

    @Resource
    private DepotService depotService;

    @Resource
    private UserService userService;

    @Resource
    private SystemConfigController systemConfigController;

    @Override
    public Map<Long, String> getMaterialListMapByHeaderId(List<Long> idList) {
        HashMap<Long, String> materialListMap = new HashMap<>();
        List<Map<String, Object>> materialListMapRes = materialMapper.getMaterialListMap(idList);
        if(materialListMapRes == null || materialListMap.isEmpty()){
            return null;
        }
        for (Map<String, Object> map:
             materialListMapRes) {
            materialListMap.put((Long) map.get("id"),(String) map.get("materialList"));
        }
        return materialListMap;
    }

    @Override
    public JSONArray findBySelect(Long categoryId, String q, String mpList, Long depotId, String enableSerialNumber, String enableBatchNumber, Integer currentPage, Integer pageSize, String token) {
        JSONArray array = new JSONArray();
        String[] mpArr = new String[]{};
        if(StringUtils.hasText(mpList)){
            mpArr = mpList.split(",");
        }
        if(StringUtils.hasText(q)){
            q = q.replaceAll(",","");
            q = q.trim();
        }
        List<Long> idList = materialCategoryService.getIdListByParentId(categoryId);
        List<MaterialExUnit> materialExUnitList = materialMapper.getMaterialListBySelectWithBarCode(
                idList, q, depotId, enableSerialNumber, enableBatchNumber, (currentPage - 1) * pageSize,pageSize);
        if(materialExUnitList != null){
            for (MaterialExUnit material:
                 materialExUnitList) {
                JSONObject obj = new JSONObject();
                String ratioStr = "";
                String commodityUnit = material.getCommodityUnit();
                if(material.getUnitId() != null){
                    Unit unit = unitService.getById(material.getUnitId());
                    if(commodityUnit.equals(unit.getBasicUnit())){
                        ratioStr = "[基本]";
                    }else if(commodityUnit.equals(unit.getOtherUnit())){
                        ratioStr = "[" + unit.getRatio() + unit.getBasicUnit() + "]";
                    }else if(commodityUnit.equals(unit.getOtherUnitTwo())){
                        ratioStr = "[" + unit.getRatioTwo() + unit.getBasicUnit() + "]";
                    }else if(commodityUnit.equals(unit.getOtherUnitThree())){
                        ratioStr = "[" + unit.getRatioThree() + unit.getBasicUnit() + "]";
                    }
                }
                obj.put("id", material.getMeId());
                obj.put("mBarCode", material.getMBarCode());
                obj.put("name", material.getName());
                obj.put("categoryName", material.getCategoryName());
                obj.put("standard", material.getStandard());
                obj.put("model", material.getModel());
                obj.put("color", material.getColor());
                obj.put("unit", commodityUnit + ratioStr);
                obj.put("sku", material.getSku());
                obj.put("enableSerialNumber", material.getEnableSerialNumber());
                obj.put("enableBatchNumber", material.getEnableBatchNumber());
                BigDecimal stock = depotItemService.getStock(depotId, material, token);
                obj.put("stock", stock);

                obj.put("expand",getMaterialOtherByParam(mpArr, material));
                obj.put("imgName", material.getImgName());
                array.add(obj);
            }
        }
        return array;
    }

    /**
     * 构造扩展信息
     * @param mpArr
     * @param m
     * @return
     */
    @Override
    public String getMaterialOtherByParam(String[] mpArr, MaterialExUnit m) {
        String materialOther = "";
        for (int i = 0; i < mpArr.length; i++) {
            if (mpArr[i].equals("制造商")) {
                materialOther = materialOther + ((m.getMfrs() == null || m.getMfrs().equals("")) ? "" : "(" + m.getMfrs() + ")");
            }
            if (mpArr[i].equals("自定义1")) {
                materialOther = materialOther + ((m.getOtherField1() == null || m.getOtherField1().equals("")) ? "" : "(" + m.getOtherField1() + ")");
            }
            if (mpArr[i].equals("自定义2")) {
                materialOther = materialOther + ((m.getOtherField2() == null || m.getOtherField2().equals("")) ? "" : "(" + m.getOtherField2() + ")");
            }
            if (mpArr[i].equals("自定义3")) {
                materialOther = materialOther + ((m.getOtherField3() == null || m.getOtherField3().equals("")) ? "" : "(" + m.getOtherField3() + ")");
            }
        }
        return materialOther;
    }

    @Override
    public List<MaterialExUnit> getMaterialListByBarCode(String barCode, Long depotId, String mpList, String prefixNo, String token) {
        String[] barCodeList = barCode.split(",");
        String[] mpArr = new String[]{};
        if(StringUtils.hasText(mpList)){
            mpArr = mpList.split(",");
        }
        List<MaterialExUnit> list = materialMapper.getMaterialListByBarCode(barCodeList);
        for (MaterialExUnit material:
             list) {
            material.setMaterialOther(getMaterialOtherByParam(mpArr, material));
            if ("LSCK".equals(prefixNo) || "LSTH".equals(prefixNo)) {
                //零售价
                material.setBillPrice(material.getCommodityDecimal());
            } else if ("CGDD".equals(prefixNo) || "CGRK".equals(prefixNo) || "CGTH".equals(prefixNo)
                    || "QTRK".equals(prefixNo) || "DBCK".equals(prefixNo) || "ZZD".equals(prefixNo) || "CXD".equals(prefixNo)
                    || "PDLR".equals(prefixNo) || "PDFP".equals(prefixNo)) {
                //采购价
                material.setBillPrice(material.getPurchaseDecimal());
            } else if ("XSDD".equals(prefixNo) || "XSCK".equals(prefixNo) || "XSTH".equals(prefixNo) || "QTCK".equals(prefixNo)) {
                //销售价
                material.setBillPrice(material.getWholesaleDecimal());
            }
            if(depotId == null){
                SystemConfig systemConfig = systemConfigController.getCurrent();
                String depotFlag = systemConfig.getDepotFlag();
                User user = userService.getLoginUser(token);
                JSONArray depot = depotService.findDepotByUserId(user.getId(), depotFlag);
                for (Object obj:
                     depot) {
                    JSONObject depotObj = JSONObject.parseObject(obj.toString());
                    if (depotObj.get("isDefault") != null) {
                        Boolean isDefault = depotObj.getBoolean("isDefault");
                        if (isDefault) {
                            Long id = depotObj.getLong("id");
                            if (!"CGDD".equals(prefixNo) && !"XSDD".equals(prefixNo)) {
                                //除订单之外的单据才有仓库
                                material.setDepotId(id);
                            }
                        }
                    }
                }
            }else{
                material.setDepotId(depotId);
            }
            BigDecimal stock = depotItemService.getStock(material.getDepotId(), material, token);
            material.setStock(stock);
        }

        return list;
    }

    @Override
    public Unit getUnitByMaterialId(Long materialId) {
        Unit unit = materialMapper.getUnitByMaterialId(materialId);
        if(unit != null){
            return unit;
        }
        return new Unit();

    }

    @Override
    public List<MaterialExUnit> getMaterialListByBarCode(String barCode) {
        String[] barCodeList = barCode.split(",");
        return materialMapper.getMaterialListByBarCode(barCodeList);
    }

    @Override
    public Long getCountBySelect(Long categoryId, String q, String mpList, Long depotId, String enableSerialNumber, String enableBatchNumber) {
        if(StringUtils.hasText(q)){
            q = q.replaceAll(",","");
            q = q.trim();
        }
        List<Long> idList = materialCategoryService.getIdListByParentId(categoryId);
        return materialMapper.getMaterialCountBySelectWithBarCode(idList, q, depotId, enableSerialNumber, enableBatchNumber);
    }

}




