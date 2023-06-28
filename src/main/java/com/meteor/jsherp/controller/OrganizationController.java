package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.domain.extand.BaseTreeNode;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.OrganizationService;
import com.meteor.jsherp.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/organization")
@RestController
@Slf4j
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;

    /**
     * 获取系统内组织的树信息
     * @param id
     * @return
     */
    @GetMapping("/getOrganizationTree")
    public JSONArray getOrganizationTree(@RequestParam("id") Long id){
        JSONArray array = new JSONArray();
        List<BaseTreeNode> organList = organizationService.getOrganizationTreeById(id);
        if(organList != null && organList.size() > 0){
            for (BaseTreeNode treeNode:
                 organList) {
                array.add(JSONObject.parseObject(JSON.toJSONString(treeNode)));
            }
        }
        return array;
    }

    @GetMapping("/findById")
    public BaseResponse findById(@RequestParam("id") Long id){
        BaseResponse response = new BaseResponse();
        try {
            JSONObject data = organizationService.findById(id);
            ResponseUtil.resSuccess(response, data);
        } catch (Exception exception) {
            log.error("获取数据异常", exception);
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }
}
