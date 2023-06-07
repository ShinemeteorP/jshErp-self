package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.MaterialInitialStock;
import com.meteor.jsherp.mapper.MaterialInitialStockMapper;
import com.meteor.jsherp.service.MaterialInitialStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_material_initial_stock(产品初始库存)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MaterialInitialStockServiceImpl extends CommonServiceImpl<MaterialInitialStockMapper, MaterialInitialStock>
    implements MaterialInitialStockService {

    @Resource
    private MaterialInitialStockMapper materialInitialStockMapper;

    @Override
    public BigDecimal getInitStockByDepotAndMaterial(List<Long> depotIdList, Long mid) {
        return materialInitialStockMapper.getInitialStock(depotIdList, mid);

    }
}




