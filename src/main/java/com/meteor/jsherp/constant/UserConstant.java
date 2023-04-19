package com.meteor.jsherp.constant;

/**
 * @author 刘鑫
 * @version 1.0
 */
public interface UserConstant {

    /**
     * 枚举列的实例
     */
    int USER_STATUS_ALLOW = 0;
    int USER_STATUS_DELETE = 1;
    int USER_STATUS_FORBID= 2;
    String LOGIN_USER_NAME_IN_REDIS = "loginUser";
    String USER_ROLE_TYPE_PERSONAL = "个人数据";
    String USER_ROLE_TYPE_ORGANIZATION = "机构数据";

    /**
     * userbusiness 的type常量
     */
    String USER_BUSINESS_USER_ROLE = "UserRole";
    String USER_BUSINESS_USER_DEPOT = "UserDepot";
    String USER_BUSINESS_USER_CUSTOMER = "UserCustomer";
    String USER_BUSINESS_ROLE_FUNCTION = "RoleFunctions";

    /**
     * 使用频繁的列名
     */
    String CURRENT_USER_ID = "id";
    String USER_LOGIN_NAME = "login_name";
    String USER_PASSWORD = "password";
    String USER_STATUS = "Status";
    String CURRENT_USER_NUM_LIMIT = "userNumLimit";
    String CURRENT_USER_ROLE_TYPE = "roleType";
    String CURRENT_USER_CLIENT_IP = "clientIP";
    /**
     * 以下为用户相关异常 常量，由于前端会根据具体常量的内容进行判断，从而输出不同的消息，可优化为与异常一起处理
     */
    /**
     * 用户不存在
     */
    String LOGIN_NAME_NOT_EXIST = "user is not exist";



    /**
     * 用户密码错误
     */
    String USER_PASSWORD_ERROR = "user password error";

    /**
     * 用户被加入黑名单
     */
    String BLACK_USER = "user is black";

    /**
     * 可以登录
     */
    String USER_CONDITION_FIT = "user can login";

    /**
     * 访问数据库异常
     */
    String USER_ACCESS_EXCEPTION = "access service error";

    /**
     * 租户被加入黑名单
     */
    String BLACK_TENANT = "tenant is black";

    /**
     * 租户已经过期
     */
    String EXPIRE_TENANT = "tenant is expire";

    /**
     * 用户已登入
     */
    String USER_ALREADY_LOGIN = "user already login";

    /**
     * 服务层发生异常
     */

    String USER_SERVICE_LOGIN_EXCEPTION = "access service exception";
}
