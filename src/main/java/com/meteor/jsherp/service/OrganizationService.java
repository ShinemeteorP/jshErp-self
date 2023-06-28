package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.domain.Organization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.extand.BaseTreeNode;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 获取除当前组织id以外的组织树节点列表
     * @return
     */
    List<BaseTreeNode> getOrganizationTreeById(Long currentId);

    /**
     * 根据id返回机构对象所有相关信息
     * @param id
     * @return
     */
    JSONObject findById(Long id);

    @Transactional(rollbackFor = Exception.class)
    int deleteBatchIds(String ids, String token);
}
