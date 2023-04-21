package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.OrgaUserRel;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.mapper.OrgaUserRelMapper;
import com.meteor.jsherp.service.OrgaUserRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 刘鑫
* @description 针对表【jsh_orga_user_rel(机构用户关系表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class OrgaUserRelServiceImpl extends CommonServiceImpl<OrgaUserRelMapper, OrgaUserRel>
    implements OrgaUserRelService{

    @Resource
    private OrgaUserRelMapper orgaUserRelMapper;

    @Override
    public long[] getUserIdListByRole(long userId, String roleType) {
        switch (roleType){
            case UserConstant.USER_ROLE_TYPE_PERSONAL:
                return new long[]{userId};
            case UserConstant.USER_ROLE_TYPE_ORGANIZATION:
                return orgaUserRelMapper.getUserIdListGroupByOrganization(userId);

        }

        return null;
    }
}




