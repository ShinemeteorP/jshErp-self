package com.meteor.jsherp.utils;

import com.meteor.jsherp.response.BaseResponse;

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
        response.setData(msg);
    }

}
