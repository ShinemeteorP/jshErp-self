package com.meteor.jsherp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private int code;
    private Object data;
    private String msg;
    public BaseResponse(int code, Object data){
        this.data = data;
        this.code = code;
    }

}
