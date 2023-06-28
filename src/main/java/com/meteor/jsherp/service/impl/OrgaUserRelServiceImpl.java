package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.OrgaUserRel;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.mapper.OrgaUserRelMapper;
import com.meteor.jsherp.service.OrgaUserRelService;
import com.meteor.jsherp.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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

    @Resource
    private UserService userService;

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

    @Override
    public int addOrgaUserRel(OrgaUserRel orgaUserRel, String token) {
        User user = userService.getLoginUser(token);
        orgaUserRel.setUpdateTime(new Date());
        orgaUserRel.setCreateTime(new Date());
        orgaUserRel.setUpdater(user.getId());
        orgaUserRel.setCreator(user.getId());
        return orgaUserRelMapper.insert(orgaUserRel);
    }

    @Override
    public int updateOrgeUserRel(OrgaUserRel orgaUserRel, String token) {
        User user = userService.getLoginUser(token);
        orgaUserRel.setUpdateTime(new Date());
        orgaUserRel.setUpdater(user.getId());
        return orgaUserRelMapper.updateById(orgaUserRel);
    }
}




