package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.MaterialInitialStock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_material_initial_stock(产品初始库存)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface MaterialInitialStockService extends IService<MaterialInitialStock>, CommonService<MaterialInitialStock> {

    /**
     * 根据仓库和物料获取初始库存
     * @param depotIdList 仓库id列表
     * @param id 物料id
     * @return
     */
    BigDecimal getInitStockByDepotAndMaterial(List<Long> depotIdList, Long id);
}
