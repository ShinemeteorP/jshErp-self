package com.meteor.jsherp.utils;

import com.meteor.jsherp.response.BaseResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 * 封装返回使用频繁的BaseResponse方法工具类
 */
public class ResponseUtil {
    public static BaseResponse resSuccess(Object data){
        return new BaseResponse(200, data);
    }

    public static void resSuccess(BaseResponse response, Object data){
        response.setCode(200);
        response.setData(data);
    }
    public static void resSuccessMsg(BaseResponse response, String msg){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", msg);
        response.setCode(200);
        response.setData(map);
    }
    public static void resSuccessMsg(BaseResponse response, Map data, String msg){
        data.put("message", msg);
        response.setCode(200);
        response.setData(data);
    }
    public static void resSuccess(BaseResponse response, Object data, String msg){
        response.setCode(200);
        response.setData(data);
        response.setMsg(msg);
    }

    public static BaseResponse defaultServiceExceptionResponse(){
        return new BaseResponse(500,"获取数据异常");
    }
    public static BaseResponse customServiceExceptionResponse(String msg){
        return new BaseResponse(500,msg);
    }
    public static void defaultServiceExceptionResponse(BaseResponse response){
        response.setData("获取数据异常");
        response.setCode(500);
    }
    public static void customServiceExceptionResponse(String msg, BaseResponse response){
        response.setCode(500);
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", msg);
        response.setData(map);
    }

    public static BaseResponse customServiceExceptionResponse(int code, String msg){
        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        return new BaseResponse(code, msg);
    }


}
