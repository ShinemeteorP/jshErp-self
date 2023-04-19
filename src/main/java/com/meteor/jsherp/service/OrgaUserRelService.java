package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.OrgaUserRel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.User;

/**
* @author 刘鑫
* @description 针对表【jsh_orga_user_rel(机构用户关系表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface OrgaUserRelService extends IService<OrgaUserRel>, CommonService<OrgaUserRel> {

    /**
     * 根据用户获取相关的用户id列表，只有个人权限只返回自己id，有组织权限，则返回组织所有用户id
     * @param userId 目前登入用户id
     * @param roleType 目前登入用户数据权限类别
     * @return
     */
    long[] getUserIdListByRole(long userId, String roleType);

}
