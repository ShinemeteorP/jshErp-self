package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 刘鑫
* @description 针对表【jsh_user(用户表)】的数据库操作Service
* @createDate 2023-04-13 18:07:56
*/
public interface UserService extends IService<User>, CommonService<User> {
    User userLogin(String loginName, String password);

    /**
     * 登入的业务逻辑实现
     * @param loginName 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    String login(String loginName, String  password) throws Exception;

    /**
     * 获取目前租户下，合法用户的数量
     * @return
     */
    long countNormalUser();

    /**
     * 将当前登入对象存入userService中
     * @param token
     * @param currentUser
     */
    void saveLoginUser(String token, User currentUser);

    /**
     * 根据token获取当前登入对象
     * @param token
     * @return
     */
    User getLoginUser(String token);

    /**
     * 退出登入，移除掉map中的user对象
     * @param token
     */
    void logOut(String token);
}
