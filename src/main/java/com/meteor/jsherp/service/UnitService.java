package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Unit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
* @author 刘鑫
* @description 针对表【jsh_unit(多单位表)】的数据库操作Service
* @createDate 2023-04-13 18:07:56
*/
public interface UnitService extends IService<Unit>, CommonService<Unit> {

    /**
     * 根据单位和原商品单位将库存进行单位转
     * @param unit
     * @param stock
     * @param commodityUnit
     * @return
     */
    BigDecimal parseStockByUnit(Unit unit, BigDecimal stock, String commodityUnit);

    /**
     * 根据materialId获取对应的Unit对象
     * @param materialId
     * @return
     */
    Unit getUnitByMId(Long materialId);
}
