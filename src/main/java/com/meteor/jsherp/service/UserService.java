package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.extand.UserEx;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

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

    /**
     * 检验用户名是否存在
     * @param
     */
    void checkLoginName(Long id, String loginName);

    /**
     * 进行用户注册
     * @param user 待注册的用户
     */
    void register(UserEx user);

    /**
     * 在用户管理页面添加一个用户
     * @param obj  添加的用户对象的相关数据
     * @param token
     */
    void addUser(JSONObject obj, String token) throws NoSuchAlgorithmException;
    /**
     * 在用户管理页面修改用户信息
     * @param obj  添加的用户对象的相关数据
     * @param token
     */
    void updateUser(JSONObject obj, String token);

    /**
     * 根据用户id重置密码为123456
     * @param id
     * @return
     */
    int resetPwdById(Long id) throws NoSuchAlgorithmException;

    /**
     * 根据id列表批量修改用户状态
     * @param ids
     * @param status
     * @return
     */
    int batchSetStatus(String ids, Integer status, String token);

    /**
     * 删除和批量删除用户
     * @param ids
     * @param token
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    int deleteBatchIds(String ids, String token);
}
