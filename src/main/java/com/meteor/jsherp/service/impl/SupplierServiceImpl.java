package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Supplier;
import com.meteor.jsherp.mapper.SupplierMapper;
import com.meteor.jsherp.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_supplier(供应商/客户信息表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier>
    implements SupplierService{

    @Resource
    private SupplierMapper supplierMapper;

    @Override
    public List<Supplier> findBySelect(String type) {
        QueryWrapper<Supplier> wrapper = new QueryWrapper<Supplier>().like("type", type).eq("enabled", true)
                .orderByAsc("sort").orderByDesc("id");
        List<Supplier> suppliers = supplierMapper.selectList(wrapper);
        return suppliers;
    }
}




