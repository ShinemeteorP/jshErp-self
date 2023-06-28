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

    /**
     * 查找当前租户下有效的会员列表信息
     * @return
     */
    JSONArray findBySelectMember();

    /**
     * 查找当前租户下有效的供应商列表信息
     * @return
     */
    JSONArray findBySelectSup();

    /**
     *查找当前租户下有效的客户信息，根据系统的客户设置开关和用户客户权限，返回部分符合权限的客户信息
     * @return
     */
    JSONArray findBySelectCus(String token);
    /**
     *查找当前租户下有效的供应商列表信息和
     * 当前租户下有效的客户信息，根据系统的客户设置开关和用户客户权限，返回部分符合权限的客户信息
     * @return
     */
    JSONArray findBySelectOrgan(String token);
}
