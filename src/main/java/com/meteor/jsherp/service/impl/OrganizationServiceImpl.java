package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Organization;
import com.meteor.jsherp.mapper.OrganizationMapper;
import com.meteor.jsherp.service.OrganizationService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_organization(机构表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization>
    implements OrganizationService{

}




