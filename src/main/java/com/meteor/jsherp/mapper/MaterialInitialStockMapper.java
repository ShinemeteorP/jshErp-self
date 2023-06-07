package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.MaterialInitialStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_material_initial_stock(产品初始库存)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:55
* @Entity generator.domain.MaterialInitialStock
*/
public interface MaterialInitialStockMapper extends BaseMapper<MaterialInitialStock> {

    BigDecimal getInitialStock(List<Long> depotIdList, Long mid);
}




