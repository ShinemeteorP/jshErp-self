package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 刘鑫
* @description 针对表【jsh_role(角色表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface RoleService extends IService<Role> {

    Role getRoleByUserId(long userId);

}
