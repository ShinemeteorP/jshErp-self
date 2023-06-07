package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.domain.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_supplier(供应商/客户信息表)】的数据库操作Service
* @createDate 2023-04-13 18:07:56
*/
public interface SupplierService extends IService<Supplier>, CommonService<Supplier> {

    /**
     * 根据类型从supplier表中获取对应的列表
     * @param type 类型
     * @return
     */
    List<Supplier> findBySelect(String type);

    /**
     * 根据id修改对应的 供应商的预付款
     * @param id
     * @param advanceIn
     * @return
     */
    int updateAdvanceInById(Long id, BigDecimal advanceIn);
}
