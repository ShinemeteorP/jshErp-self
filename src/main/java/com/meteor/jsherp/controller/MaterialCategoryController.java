package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.domain.extand.BaseTreeNode;
import com.meteor.jsherp.service.MaterialCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestController
@RequestMapping("/materialCategory")
public class MaterialCategoryController {

    @Resource
    private MaterialCategoryService materialCategoryService;

    @GetMapping("/getMaterialCategoryTree")
    public JSONArray getMaterialCategoryTree(@RequestParam("id") Long id) {
        JSONArray array = new JSONArray();

        List<BaseTreeNode> nodeList = materialCategoryService.getMaterialCategoryTree(id);
        if (nodeList != null && nodeList.size() > 0) {
            for (BaseTreeNode node :
                    nodeList) {
                array.add(JSON.parseObject(JSON.toJSONString(node)));
            }
        }

        return array;
    }

}
