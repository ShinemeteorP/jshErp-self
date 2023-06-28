package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.Supplier;
import com.meteor.jsherp.service.SupplierService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("supplier")
@RestController
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    @PostMapping("/findBySelect_retail")
    public JSONArray findBySelect_retail() {
        JSONArray array = null;

        array = supplierService.findBySelectMember();

        return array;
    }

    @PostMapping("/findBySelect_sup")
    public JSONArray findBySelect_sup() {
        JSONArray array = null;

        array = supplierService.findBySelectSup();

        return array;
    }

    @PostMapping("/findBySelect_cus")
    public JSONArray findBySelect_cus(@RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        JSONArray array = null;

        array = supplierService.findBySelectCus(token);

        return array;
    }

    /**
     * 查找往来单位，含供应商和客户信息-下拉框
     *
     * @return
     */
    @PostMapping("/findBySelect_organ")
    public JSONArray findBySelect_organ(@RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        JSONArray array = null;

        array = supplierService.findBySelectOrgan(token);

        return array;
    }
}
