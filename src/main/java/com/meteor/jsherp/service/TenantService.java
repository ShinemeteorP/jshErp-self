package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 刘鑫
* @description 针对表【jsh_tenant(租户)】的数据库操作Service
* @createDate 2023-04-13 18:07:56
*/
public interface TenantService extends IService<Tenant>, CommonService<Tenant> {
//    Tenant getTenantByKey(String key, Object value);

    /**
     * 根据用户的tenant_id查询对应租户
     * @param tenantId tenant_id
     * @return
     */
    Tenant getByUserId(long tenantId);
}
