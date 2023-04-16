package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Tenant;
import com.meteor.jsherp.mapper.TenantMapper;
import com.meteor.jsherp.service.TenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 刘鑫
* @description 针对表【jsh_tenant(租户)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
public class TenantServiceImpl extends CommonServiceImpl<TenantMapper, Tenant>
    implements TenantService{

    @Resource
    private TenantMapper tenantMapper;

    @Override
    public Tenant getByUserId(long tenantId) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<Tenant>().eq("tenant_id", tenantId);

        return tenantMapper.selectOne(queryWrapper);
    }

//    @Override
//    public Tenant getTenantByKey(String key, Object value) {
//        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq(key, value);
//        Tenant tenant = tenantMapper.selectOne(queryWrapper);
//        return tenant;
//    }
}




