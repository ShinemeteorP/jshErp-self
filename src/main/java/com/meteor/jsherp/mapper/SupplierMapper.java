package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
* @author 刘鑫
* @description 针对表【jsh_supplier(供应商/客户信息表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:55
* @Entity generator.domain.Supplier
*/
public interface SupplierMapper extends BaseMapper<Supplier> {

    /**
     * 根据id 修改预付款金额
     * @param id
     * @param advanceIn
     * @return
     */
    int updateAdvanceInById(Long id, BigDecimal advanceIn);
}




