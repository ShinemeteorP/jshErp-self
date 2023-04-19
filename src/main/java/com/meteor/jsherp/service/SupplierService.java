package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.domain.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_supplier(供应商/客户信息表)】的数据库操作Service
* @createDate 2023-04-13 18:07:56
*/
public interface SupplierService extends IService<Supplier> {

    /**
     * 根据类型从supplier表中获取对应的列表
     * @param type 类型
     * @return
     */
    List<Supplier> findBySelect(String type);
}
