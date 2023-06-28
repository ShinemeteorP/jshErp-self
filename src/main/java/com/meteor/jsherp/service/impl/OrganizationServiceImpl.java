package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Organization;
import com.meteor.jsherp.domain.Supplier;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.domain.extand.BaseTreeNode;
import com.meteor.jsherp.exception.BusinessException;
import com.meteor.jsherp.mapper.OrganizationMapper;
import com.meteor.jsherp.service.OrganizationService;
import com.meteor.jsherp.service.SupplierService;
import com.meteor.jsherp.service.UserBusinessService;
import com.meteor.jsherp.service.common.ResourceInfo;
import com.meteor.jsherp.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_organization(机构表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
@ResourceInfo("organization")
public class OrganizationServiceImpl extends CommonServiceImpl<OrganizationMapper, Organization>
    implements OrganizationService{

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private LogServiceImpl logService;

    @Override
    public List<Organization> getOrangArray(Long id, String subType, String purchaseStatus) {
        List<Organization> res = null;
        UserBusiness userBusiness = userBusinessService.getOneByKeyMap(
                ImmutableMap.of("key_id", id, "type", UserConstant.USER_BUSINESS_USER_CUSTOMER));
        List<Supplier> suppliers = supplierService.findBySelect("客户");
        if (subType.contains("销售")){

        }
        return res;
    }

    @Override
    public List<BaseTreeNode> getOrganizationTreeById(Long currentId) {
        return organizationMapper.getOrganizationTree(currentId);
    }

    @Override
    public JSONObject findById(Long id) {
        Organization organization = organizationMapper.selectById(id);
        if(organization != null){
            JSONObject obj = new JSONObject();
            obj.put("id", organization.getId());
            obj.put("orgAbr", organization.getOrgAbr());
            obj.put("parentId", organization.getParentId());
            obj.put("orgNo", organization.getOrgNo());
            obj.put("sort", organization.getSort());
            obj.put("remark", organization.getRemark());
            if(organization.getParentId() != null) {
                Organization parentOrg = organizationMapper.selectById(organization.getParentId());
                obj.put("orgParentName", parentOrg.getOrgAbr());
            }
            return obj;
        }
        return null;
    }

    @Override
    public int checkName(Long id, String name) {
        QueryWrapper<Organization> queryWrapper = new QueryWrapper<Organization>().eq("org_abr", name).ne(id != null, "id", id);
        Integer integer = organizationMapper.selectCount(queryWrapper);
        return integer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addObj(JSONObject obj, String token) {
        Organization organization = JSONObject.parseObject(obj.toJSONString(), Organization.class);
        organization.setCreateTime(new Date());
        organization.setUpdateTime(new Date());
        int insert = organizationMapper.insert(organization);
        logService.insertLog("机构", "新增机构，id = " + organization.getId(), 1);
        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateObj(JSONObject obj, String token) {
        Organization organization = JSONObject.parseObject(obj.toJSONString(), Organization.class);
        organization.setUpdateTime(new Date());
        int insert = organizationMapper.updateById(organization);
        logService.insertLog("机构", "修改机构，id = " + organization.getId(), 1);
        return insert;
    }

    @Override
    public int deleteBatch(String ids, String token) {
        return deleteBatchIds(ids, token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatchIds(String ids, String token) {
        List<Long> idList = StringUtil.strToLongList(ids);
        Date now = new Date();
        QueryWrapper<Organization> queryWrapper = new QueryWrapper<Organization>().in("parent_id", idList);
        List<Organization> childrenOrga = organizationMapper.selectList(queryWrapper);
        if(childrenOrga != null && childrenOrga.size() > 0){
            throw new BusinessException(ExceptionConstant.ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_CODE, ExceptionConstant.ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_MSG);
        }
        UpdateWrapper<Organization> updateWrapper = new UpdateWrapper<Organization>().in("id", idList).set("update_time", now);
        organizationMapper.update(null, updateWrapper);
        int delete = organizationMapper.deleteBatchIds(idList);
        logService.insertLog("机构", "删除机构 id 为" + idList, 1);
        return delete;
    }

    @Override
    public int delete(Long id, String token) {
        return deleteBatchIds(id.toString(), token);
    }
}




