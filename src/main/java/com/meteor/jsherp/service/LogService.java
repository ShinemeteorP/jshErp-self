package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Log;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 刘鑫
* @description 针对表【jsh_log(操作日志)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface LogService extends IService<Log> {
    /**
     * 用户已经登入过的新增日志方法，从登录态中获取登入用户id
     * @param modelName
     * @param content
     * @param status
     */
    void insertLog(String modelName, String content, Integer status);

    /**
     * 用户登入之前的新增日志方法，直接将用户id传参过来
     * @param modelName
     * @param content
     * @param status
     * @param userId
     */
    void insertLogWithUserId(String modelName, String content, Integer status, Long userId);
}
