package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.Log;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.mapper.LogMapper;
import com.meteor.jsherp.service.LogService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.utils.CommonUtil;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
* @author 刘鑫
* @description 针对表【jsh_log(操作日志)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService {

    @Resource
    private LogMapper logMapper;

    @Resource
    private UserService userService;

    @Override
    public void insertLog(String modelName, String content, Integer status){
        Log log = new Log();
        log.setOperation(modelName);
        log.setContent(content);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader(ErpAllConstant.REQUEST_TOKEN_KEY);
        User user = userService.getLoginUser(token);
        if(user != null){
            log.setUserId(user.getId());
        }
        log.setClientIp(CommonUtil.getLocalIp(request));
        Date date = new Date();
        log.setCreateTime(date);
        QueryWrapper<Log> queryWrapper = new QueryWrapper<Log>().eq("user_id", user.getId()).
                eq("client_ip", log.getClientIp()).eq("create_time", log.getCreateTime()).
                eq("operation", log.getOperation());
        Integer count = logMapper.selectCount(queryWrapper);
        if(count > 0){
            userService.logOut(token);
        }
        log.setStatus(status);
        logMapper.insert(log);
    }

    @Override
    public void insertLogWithUserId(String modelName, String content, Integer status, Long userId){
        Log log = new Log();
        log.setOperation(modelName);
        log.setContent(content);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader(ErpAllConstant.REQUEST_TOKEN_KEY);
        log.setUserId(userId);
        log.setClientIp(CommonUtil.getLocalIp(request));
        Date date = new Date();
        log.setCreateTime(date);
        QueryWrapper<Log> queryWrapper = new QueryWrapper<Log>().eq("user_id", userId).
                eq("client_ip", log.getClientIp()).eq("create_time", log.getCreateTime()).
                eq("operation", log.getOperation());
        Integer count = logMapper.selectCount(queryWrapper);
        if(count > 0){
            userService.logOut(token);
        }
        log.setStatus(status);
        logMapper.insert(log);
    }

}




