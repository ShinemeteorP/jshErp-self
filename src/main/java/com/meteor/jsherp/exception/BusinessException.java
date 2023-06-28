package com.meteor.jsherp.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Data
public class BusinessException extends RuntimeException{
    private int code;
    private Map<String, Object> data;

    public BusinessException(){}

    public BusinessException(int code, Map data){
        super();
        this.code = code;
        this.data = data;
    }

    public BusinessException(int code, String msg){
        super(msg);
        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        this.code = code;
        this.data = map;
    }

    public String getMessage(){
        if(data == null){
            return null;
        }
        String message = (String) data.get("message");
        return message;
    }
}
