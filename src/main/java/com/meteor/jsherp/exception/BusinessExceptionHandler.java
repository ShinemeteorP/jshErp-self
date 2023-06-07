package com.meteor.jsherp.exception;


import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestControllerAdvice
public class BusinessExceptionHandler{

    @ExceptionHandler(Exception.class)
    public JSONObject resolveException(Exception e){
        JSONObject object = new JSONObject();
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        object.put("data", map);
        return object;
    }
}
