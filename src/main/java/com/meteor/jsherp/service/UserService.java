package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 刘鑫
* @description 针对表【jsh_user(用户表)】的数据库操作Service
* @createDate 2023-04-13 18:07:56
*/
public interface UserService extends IService<User>, CommonService<User> {
    /**
     * 登入的业务逻辑实现
     * @param loginName 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    String login(String loginName, String  password) throws Exception;

    long countNormalUser();
}
