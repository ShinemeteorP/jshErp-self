package com.meteor.jsherp.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "通用响应", description = "控制器的通用响应")
public class BaseResponse {
    @ApiModelProperty("响应码")
    private int code;
    @ApiModelProperty("响应数据")
    private Object data;
    @ApiModelProperty("响应状态描述")
    private String msg;
    public BaseResponse(int code, Object data){
        this.data = data;
        this.code = code;
    }

}
