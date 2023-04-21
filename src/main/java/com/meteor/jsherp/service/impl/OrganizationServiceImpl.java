package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Organization;
import com.meteor.jsherp.domain.Supplier;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.OrganizationMapper;
import com.meteor.jsherp.service.OrganizationService;
import com.meteor.jsherp.service.SupplierService;
import com.meteor.jsherp.service.UserBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_organization(机构表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class OrganizationServiceImpl extends CommonServiceImpl<OrganizationMapper, Organization>
    implements OrganizationService{

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private SupplierService supplierService;

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
}




