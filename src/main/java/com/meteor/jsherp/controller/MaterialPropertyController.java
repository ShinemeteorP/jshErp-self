package com.meteor.jsherp.controller;

import com.meteor.jsherp.domain.MaterialProperty;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.MaterialPropertyService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/materialProperty")
@RestController
public class MaterialPropertyController {

    @Resource
    private MaterialPropertyService materialPropertyService;

    @GetMapping("/getAllList")
    public BaseResponse getAllList() {
        BaseResponse response = new BaseResponse();

        List<MaterialProperty> list = materialPropertyService.list();
        ResponseUtil.resSuccess(response, list);

        return response;
    }
}
