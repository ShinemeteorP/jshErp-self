package com.meteor.jsherp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.DepotHead;
import com.meteor.jsherp.domain.DepotItem;
import com.meteor.jsherp.domain.SerialNumber;
import com.meteor.jsherp.domain.extand.SerialNumberEx;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.DepotHeadService;
import com.meteor.jsherp.service.DepotItemService;
import com.meteor.jsherp.service.SerialNumberService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestController
@RequestMapping("serialNumber")
public class SerialNumberController {

    @Resource
    private SerialNumberService serialNumberService;

    @Resource
    private DepotItemService depotItemService;

    @Resource
    private DepotHeadService depotHeadService;


    @GetMapping("/getEnableSerialNumberList")
    public BaseResponse getEnableSerialNumberList(@RequestParam("name") String name,
                                                  @RequestParam("depotItemId") Long depotItemId,
                                                  @RequestParam("depotId") Long depotId,
                                                  @RequestParam("barCode") String barCode,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("rows") Integer rows) {
        BaseResponse response = new BaseResponse();

        String number = "";
        HashMap<String, Object> map = new HashMap<>();
        if (depotItemId != null) {
            DepotItem depotItem = depotItemService.getById(depotItemId);
            number = depotHeadService.getOneByKey("header_id", depotItem.getHeaderId()).getNumber();
        }
        List<SerialNumberEx> serialNumberList = serialNumberService.getSerialNumberListByParam(name, number, depotId, barCode, (page - 1) * rows, rows);
        for (SerialNumberEx s :
                serialNumberList) {
            s.setCreateTimeStr(new SimpleDateFormat(BusinessConstant.DEFAULT_DATETIME_FORMAT).format(s.getCreatorName()));
        }
        Long total = serialNumberService.getSerialNumberCountByParam(name, number, depotId, barCode);
        map.put("total", total);
        map.put("rows", serialNumberList);
        ResponseUtil.resSuccess(response, map);

        return response;
    }
}
