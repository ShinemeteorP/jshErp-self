package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Unit;
import com.meteor.jsherp.mapper.UnitMapper;
import com.meteor.jsherp.service.UnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
* @author 刘鑫
* @description 针对表【jsh_unit(多单位表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
public class UnitServiceImpl extends CommonServiceImpl<UnitMapper, Unit>
    implements UnitService{

    @Resource
    private UnitMapper unitMapper;

    @Override
    public BigDecimal parseStockByUnit(Unit unit, BigDecimal stock, String commodityUnit) {
        if(commodityUnit.equals(unit.getOtherUnit()) && unit.getRatio() != null && !unit.getRatio().equals(BigDecimal.ZERO)){
            stock = stock.divide(unit.getRatio(), 2, BigDecimal.ROUND_HALF_UP);
        }else if(commodityUnit.equals(unit.getOtherUnitTwo()) && unit.getRatioTwo() != null && !unit.getRatioTwo().equals(BigDecimal.ZERO)){
            stock = stock.divide(unit.getRatioTwo(), 2, BigDecimal.ROUND_HALF_UP);
        }else if(commodityUnit.equals(unit.getOtherUnitThree()) && unit.getRatioThree() != null && !unit.getRatioThree().equals(BigDecimal.ZERO)){
            stock = stock.divide(unit.getRatioThree(), 2, BigDecimal.ROUND_HALF_UP);
        }

        return stock;
    }

    @Override
    public Unit getUnitByMId(Long materialId) {
        Unit unit = unitMapper.getUnitByMaterialId(materialId);
        return unit == null ? new Unit() : unit;
    }
}




