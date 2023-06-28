package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.extand.MaterialExUnit;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.MaterialService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestController
@RequestMapping("/material")
public class MaterialController {
    @Resource
    private MaterialService materialService;

    /**
     * 根据已知的参数条件，查询符合条件的物料信息
     *
     * @param categoryId
     * @param q
     * @param mpList
     * @param depotId
     * @param enableSerialNumber
     * @param enableBatchNumber
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findBySelect")
    public JSONObject findBySelect(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                   @RequestParam(value = "q", required = false) String q,
                                   @RequestParam(value = "mpList", required = false) String mpList,
                                   @RequestParam(value = "depotId", required = false) Long depotId,
                                   @RequestParam(value = "enableSerialNumber", required = false) String enableSerialNumber,
                                   @RequestParam(value = "enableBatchNumber", required = false) String enableBatchNumber,
                                   @RequestParam("page") Integer currentPage,
                                   @RequestParam("rows") Integer pageSize,
                                   @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        JSONObject obj = null;

        obj = new JSONObject();
        JSONArray materialList = materialService.findBySelect(categoryId, q, mpList, depotId, enableSerialNumber, enableBatchNumber, currentPage, pageSize, token);
        Long total = materialService.getCountBySelect(categoryId, q, mpList, depotId, enableSerialNumber, enableBatchNumber);
        obj.put("total", total);
        obj.put("rows", materialList);

        return obj;
    }

    @GetMapping("/getMaterialByBarCode")
    public BaseResponse getMaterialByBarCode(@RequestParam("barCode") String barCode,
                                             @RequestParam(value = "depotId", required = false) Long depotId,
                                             @RequestParam("mpList") String mpList,
                                             @RequestParam(required = false, value = "prefixNo") String prefixNo,
                                             @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        List<MaterialExUnit> list = materialService.getMaterialListByBarCode(barCode, depotId, mpList, prefixNo, token);
        ResponseUtil.resSuccess(response, list);


        return response;
    }
}
