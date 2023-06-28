package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.extand.MaterialExUnit;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.DepotItemService;
import com.meteor.jsherp.service.MaterialService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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

    @Resource
    private MaterialService materialService;

    @GetMapping("/buyOrSalePrice")
    public BaseResponse buyOrSalePrice(@RequestParam(value = "roleType", required = false) String type,
                                       @RequestHeader(value = ErpAllConstant.REQUEST_TOKEN_KEY) String token) throws Exception {
        BaseResponse response = new BaseResponse();

        String approvalFlag = systemConfigController.getCurrent().getMultiLevelApprovalFlag();
        Map<String, Object> data = depotItemService.getMonthsStatics(token, type, approvalFlag);
        ResponseUtil.resSuccess(response, data);

        return response;
    }

    @GetMapping("/findStockByDepotAndBarCode")
    public BaseResponse findStockByDepotAndBarCode(@RequestParam(value = "depotId", required = false) Long depotId,
                                                   @RequestParam("barCode") String barCode,
                                                   @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        HashMap<String, Object> map = new HashMap<String, Object>();
        List<MaterialExUnit> materialExUnits = materialService.getMaterialListByBarCode(barCode);
        BigDecimal stock = BigDecimal.ZERO;
        if (materialExUnits != null && materialExUnits.size() > 0) {
            stock = depotItemService.getStock(depotId, materialExUnits.get(0), token);
        }
        map.put("stock", stock);
        ResponseUtil.resSuccess(response, map);

        return response;
    }

    /**
     * 根据header_id获取子单据以及相关的物料和仓库具体信息
     *
     * @param headerId
     * @param mpList
     * @param linkType
     * @param isReadOnly
     * @return
     */
    @GetMapping("/getDetailList")
    public BaseResponse getDetailList(@RequestParam("headerId") Long headerId,
                                      @RequestParam("mpList") String mpList,
                                      @RequestParam(value = "linkType", required = false) String linkType,
                                      @RequestParam(value = "isReadOnly", required = false) String isReadOnly,
                                      @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        JSONObject data = depotItemService.getDetailList(headerId, mpList, linkType, isReadOnly, token);
        ResponseUtil.resSuccess(response, data);

        return response;
    }
}
