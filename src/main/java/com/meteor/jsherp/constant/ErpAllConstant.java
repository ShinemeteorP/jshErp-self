package com.meteor.jsherp.constant;

/**
 * @author 刘鑫
 * @version 1.0
 */
public interface ErpAllConstant {
    String PLATFORM_NAME = "platform_name";
    String PLATFORM_URL = "platform_url";
    String PLATFORM_KEY = "platform_key";
    //请求头中的token对应的key
    String REQUEST_TOKEN_KEY = "X-Access-Token";

    //请求相关message常量
    String REQUEST_SUCCESS_MSG = "成功";
    String REQUEST_BAD_REQUEST_MSG = "请求错误或参数错误";
    String REQUEST_UNAUTHORIZED_MSG = "未认证用户";
    String REQUEST_INVALID_VERIFY_CODE_MSG = "错误的验证码";
    String REQUEST_ERROR_MSG = "服务内部错误";
    String REQUEST_WARING_MSG = "提醒信息";
    String REQUEST_REDIRECT_MSG = "session失效，重定向";
    String REQUEST_FORWARD_REDIRECT_MSG = "转发请求session失效";
    String REQUEST_FORWARD_FAILED_MSG = "转发请求失败!";
    String REQUEST_TEST_USER_MSG = "演示用户禁止操作";

    String REQUEST_PARAM_CURRENT_PAGE = "currentPage";
    String REQUEST_PARAM_PAGE_SIZE = "pageSize";
}
