package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Organization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_organization(机构表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface OrganizationService extends IService<Organization> , CommonService<Organization> {

    /**
     * 有表单据查询，获取用户有权限的，类型为客户的组织id列表
     * @param id
     * @param subType
     * @param purchaseStatus
     * @return
     */
    List<Organization> getOrangArray(Long id, String subType, String purchaseStatus);
}
