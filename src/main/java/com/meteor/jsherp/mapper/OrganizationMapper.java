package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meteor.jsherp.domain.extand.BaseTreeNode;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_organization(机构表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:55
* @Entity generator.domain.Organization
*/
public interface OrganizationMapper extends BaseMapper<Organization> {

    List<BaseTreeNode> getOrganizationTree(Long currentId);

    List<BaseTreeNode> getNextTree(Long currentId, Long parentId);
}




