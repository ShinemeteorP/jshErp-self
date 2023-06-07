package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Tenant;
import com.meteor.jsherp.mapper.TenantMapper;
import com.meteor.jsherp.service.TenantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
* @author 刘鑫
* @description 针对表【jsh_tenant(租户)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
public class TenantServiceImpl extends CommonServiceImpl<TenantMapper, Tenant>
    implements TenantService{

    @Value("${tenant.userNumLimit}")
    private Integer userNumLimit;

    @Value("${tenant.tryDayLimit}")
    private Integer expireDays;

    @Resource
    private TenantMapper tenantMapper;

    @Override
    public Tenant getByUserId(long tenantId) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<Tenant>().eq("tenant_id", tenantId);

        return tenantMapper.selectOne(queryWrapper);
    }

    @Override
    public int insertTenant(JSONObject tenantStr) {
        Tenant tenant = JSONObject.parseObject(tenantStr.toString(), Tenant.class);
        tenant.setCreateTime(new Date());
        if(tenant.getUserNumLimit() == null){
            tenant.setUserNumLimit(userNumLimit);
        }
        if(tenant.getExpireTime() == null){
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.DATE, expireDays);
            Date expireTime = calendar.getTime();
            tenant.setExpireTime(expireTime);
        }
        int insert = tenantMapper.insert(tenant);
        return insert;
    }

//    @Override
//    public Tenant getTenantByKey(String key, Object value) {
//        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq(key, value);
//        Tenant tenant = tenantMapper.selectOne(queryWrapper);
//        return tenant;
//    }
}




