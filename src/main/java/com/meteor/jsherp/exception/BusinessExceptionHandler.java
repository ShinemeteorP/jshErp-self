package com.meteor.jsherp.exception;

import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 刘鑫
 * @version 1.0
 */
@RestControllerAdvice
@Slf4j
public class BusinessExceptionHandler{

    @ExceptionHandler(Exception.class)
    public BaseResponse resolveException(Exception e){
        if(e instanceof BusinessException){
            BusinessException exception = (BusinessException) e;
            log.error("BusinessException occurred 异常码[{}]，异常信息[{}]", exception.getCode(), exception.getMessage(), exception);
            return new BaseResponse(exception.getCode(), exception.getData());
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.error("Unknow Exception Occurred -> url:{}", request.getRequestURL(), e);
        e.printStackTrace();
        return ResponseUtil.customServiceExceptionResponse(ExceptionConstant.SERVICE_SYSTEM_ERROR_MSG);
    }
}
