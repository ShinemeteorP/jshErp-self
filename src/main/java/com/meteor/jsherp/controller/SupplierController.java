package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.domain.Supplier;
import com.meteor.jsherp.service.SupplierService;
import org.springframework.web.bind.annotation.PostMapping;
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
    public JSONArray findBySelect_retail(){
        JSONArray array = new JSONArray();
        try {
            List<Supplier> dataList = supplierService.findBySelect("会员");
            for (Supplier supplier:
                 dataList) {
                JSONObject object = new JSONObject();
                object.put("id", supplier.getId());
                object.put("supplier", supplier.getSupplier());
                object.put("advanceIn", supplier.getAdvanceIn());
                array.add(object);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return array;
    }
}
