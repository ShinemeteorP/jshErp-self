package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.domain.Material;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.Unit;
import com.meteor.jsherp.domain.extand.MaterialExUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_material(产品表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface MaterialService extends IService<Material>, CommonService<Material> {

    /**
     * 通过子单据的header_id多表联查，获取物料的信息(商品简述)
     * @param idList  header_id列表
     * @return  以物料id为key，物料信息拼接的字符串为value的map
     */
    Map<Long, String> getMaterialListMapByHeaderId(List<Long> idList);

    /**
     * 根据已知的参数条件，查询符合条件的物料信息
     * @param categoryId
     * @param q
     * @param mpList
     * @param depotId
     * @param enableSerialNumber
     * @param enableBatchNumber
     * @param currentPage
     * @param pageSize
     * @return
     */
    JSONArray findBySelect(Long categoryId, String q, String mpList, Long depotId, String enableSerialNumber, String enableBatchNumber, Integer currentPage, Integer pageSize, String token);

    /**
     * 补充物料的额外补充信息
     * @param mpArr
     * @param m
     * @return
     */
    String getMaterialOtherByParam(String[] mpArr, MaterialExUnit m);

    /**
     * 根据条码信息获取物料列表
     * @param barCode
     * @param depotId
     * @param mpList
     * @param prefixNo
     * @param token
     * @return
     */
    List<MaterialExUnit> getMaterialListByBarCode(String barCode, Long depotId, String mpList, String prefixNo, String token);

    /**
     * 通过materialId查询对应的单位数据
     * @param materialId
     * @return
     */
    Unit getUnitByMaterialId(Long materialId);

    /**
     * 通过条码获取物料信息
     * @param barCode
     * @return
     */
    List<MaterialExUnit> getMaterialListByBarCode(String barCode);

    /**
     * 根据已知的参数条件，查询符合条件的物料总数量
     * @param categoryId
     * @param q
     * @param mpList
     * @param depotId
     * @param enableSerialNumber
     * @param enableBatchNumber
     * @return
     */
    Long getCountBySelect(Long categoryId, String q, String mpList, Long depotId, String enableSerialNumber, String enableBatchNumber);
}
