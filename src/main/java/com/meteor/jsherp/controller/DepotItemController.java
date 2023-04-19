package com.meteor.jsherp.controller;

import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.DepotItemService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/depotItem")
@RestController
public class DepotItemController {

    @Resource
    private DepotItemService depotItemService;

    @Resource
    private SystemConfigController systemConfigController;

    @GetMapping("/buyOrSalePrice")
    public BaseResponse buyOrSalePrice(@RequestParam(value = "roleType", required = false) String type,
                                       @RequestHeader(value = ErpAllConstant.REQUEST_TOKEN_KEY) String token){
        BaseResponse response = new BaseResponse();
        try {
            String approvalFlag = systemConfigController.getCurrent().getMultiLevelApprovalFlag();
            Map<String, Object> data = depotItemService.getMonthsStatics(token, type, approvalFlag);
            ResponseUtil.resSuccess(response,data);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }

}
