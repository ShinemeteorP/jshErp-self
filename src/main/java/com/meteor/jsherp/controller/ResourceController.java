package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.common.ResourceService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 该控制器是一个 通用资源控制器，把一个通用的查询、计数、取列表等功能集成为一个控制器，由该控制器统一管理
 * 然后根据请求的apiName参数，从Service 的 Map 容器中获取对应的Service ， 来实现数据操作
 *
 * @author 刘鑫
 * @version 1.0
 */
@RestController
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    /**
     * 通用查询列表方法
     *
     * @param apiName      模块名称
     * @param parameterMap 查询参数map
     * @param request
     * @return
     */
    @GetMapping("/{apiName}/list")
    public BaseResponse selectList(@PathVariable("apiName") String apiName, @RequestParam Map<String, String> parameterMap, HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        HashMap<String, Object> map = new HashMap<>();
        parameterMap.put("token", request.getHeader(ErpAllConstant.REQUEST_TOKEN_KEY));

        List select = resourceService.select(apiName, parameterMap);
        Integer size = resourceService.count(apiName, parameterMap);
        if (select != null) {
            map.put("message", ErpAllConstant.REQUEST_SUCCESS_MSG);
            map.put("total", size);
            map.put("rows", select);
        } else {
            map.put("message", "找不到数据");
            map.put("total", 0L);
            map.put("rows", new ArrayList<>());
        }
        ResponseUtil.resSuccess(response, map);

        return response;
    }

    /**
     * 根据id查找对应的数据
     *
     * @param apiName
     * @param id
     * @return
     */
    @GetMapping("/{apiName}/info")
    public BaseResponse info(@PathVariable("apiName") String apiName, @RequestParam("id") Long id) {
        BaseResponse response = new BaseResponse();

        Object obj = resourceService.selectOne(apiName, id);
        HashMap<String, Object> map = new HashMap<>();
        if (obj != null) {
            map.put("info", obj);
            map.put("message", ErpAllConstant.REQUEST_SUCCESS_MSG);
            ResponseUtil.resSuccess(response, map);
        } else {
            ResponseUtil.customServiceExceptionResponse(ErpAllConstant.REQUEST_NOT_FIND_ANY_DATA, response);
        }

        return response;
    }

    @DeleteMapping("/{apiName}/delete")
    public BaseResponse delete(@PathVariable("apiName") String apiName,
                               @RequestParam("id") Long id,
                               @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        HashMap<String, Object> map = new HashMap<>();
        String message;
        int count = resourceService.delete(apiName, id, token);
        if (count == 1) {
            message = ErpAllConstant.REQUEST_SUCCESS_MSG;
            response.setCode(200);
        } else if (count == -1) {
            message = ErpAllConstant.REQUEST_TEST_USER_MSG;
            response.setCode(-1);
        } else {
            throw new RuntimeException();
        }
        map.put("message", message);
        response.setData(map);

        return response;
    }

    @DeleteMapping("/{apiName}/deleteBatch")
    public BaseResponse deleteBatch(@PathVariable("apiName") String apiName,
                                    @RequestParam("ids") String ids,
                                    @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        HashMap<String, Object> map = new HashMap<>();
        String message;
        int count = resourceService.deleteBatch(apiName, ids, token);
        if (count == 1) {
            message = ErpAllConstant.REQUEST_SUCCESS_MSG;
            response.setCode(200);
        } else if (count == -1) {
            message = ErpAllConstant.REQUEST_TEST_USER_MSG;
            response.setCode(-1);
        } else {
            throw new RuntimeException();
        }
        map.put("message", message);
        response.setData(map);

        return response;
    }

    @GetMapping("/{apiName}/checkIsNameExist")
    public BaseResponse checkIsNameExist(@PathVariable("apiName") String apiName,
                                         @RequestParam("id") Long id,
                                         @RequestParam(value = "name", required = false) String name) {
        BaseResponse response = new BaseResponse();

        HashMap<Object, Object> data = new HashMap<>();
        int count = resourceService.checkIsNameExist(apiName, id, name);
        if (count > 0) {
            data.put("status", true);
        } else {
            data.put("status", false);
        }
        ResponseUtil.resSuccessMsg(response, data, ErpAllConstant.REQUEST_SUCCESS_MSG);

        return response;
    }

    @PostMapping("/{apiName}/add")
    public BaseResponse add(@PathVariable("apiName") String apiName,
                            @RequestBody JSONObject obj,
                            @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        int result = resourceService.addObj(apiName, obj, token);
        if (result > 0) {
            ResponseUtil.resSuccessMsg(response, ErpAllConstant.REQUEST_SUCCESS_MSG);
        } else {
            ResponseUtil.customServiceExceptionResponse(ErpAllConstant.REQUEST_ERROR_MSG, response);
        }

        return response;
    }

    @PutMapping("/{apiName}/update")
    public BaseResponse update(@PathVariable("apiName") String apiName,
                               @RequestBody JSONObject obj,
                               @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        int result = resourceService.updateObj(apiName, obj, token);
        if (result > 0) {
            ResponseUtil.resSuccessMsg(response, ErpAllConstant.REQUEST_SUCCESS_MSG);
        } else {
            ResponseUtil.customServiceExceptionResponse(ErpAllConstant.REQUEST_ERROR_MSG, response);
        }

        return response;
    }
}
