package com.meteor.jsherp.controller;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.domain.extand.DepotHeadExReBody;
import com.meteor.jsherp.domain.extand.DepotHeadEx;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.DepotHeadService;
import com.meteor.jsherp.service.OrgaUserRelService;
import com.meteor.jsherp.service.RedisService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.service.common.ResourceInfo;
import com.meteor.jsherp.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 */
@ResourceInfo("depotHead")
@RequestMapping("/depotHead")
@RestController
@Api("单据主表管理")
public class DepotHeadController {
    @Resource
    private DepotHeadService depotHeadService;

    @Resource
    private RedisService redisService;

    @Resource
    private UserService userService;

    @Resource
    private OrgaUserRelService orgaUserRelService;

    @Resource
    private SystemConfigController systemConfigController;

    /**
     * 获取首页监控的数据源头，年、月、日的销售、零售、采购金额数据
     *
     * @param type
     * @param token
     * @return
     */
    @GetMapping("/getBuyAndSaleStatistics")
    @ApiOperation("获取首页监控的数据源头，年、月、日的销售、零售、采购金额数据")
    public BaseResponse getBuyAndSaleStatistics(@RequestParam(value = "roleType", required = false) @ApiParam("用户权限类型") String type,
                                                @RequestHeader(value = ErpAllConstant.REQUEST_TOKEN_KEY, required = false) @ApiParam("登入状态码") String token) {
        BaseResponse response = new BaseResponse();

        String roleType = (String) redisService.queryMsgByKey(token, "roleType");
        Long userId = userService.getLoginUser(token).getId();
        long[] userIds = orgaUserRelService.getUserIdListByRole(userId, roleType);
        String approvalFlag = systemConfigController.getCurrent().getMultiLevelApprovalFlag();
        Map<String, Object> statistics = depotHeadService.getBuyAndSaleStatistics(userIds, userId, approvalFlag);
        ResponseUtil.resSuccess(response, statistics);

        return response;
    }

    /**
     * 新增一条单据记录
     *
     * @param depotHeadArg
     */
    @PostMapping("/addDepotHeadAndDetail")
    public BaseResponse addDepotHeadAndDetail(@RequestBody DepotHeadExReBody depotHeadArg, @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        depotHeadService.addDepotHeadAndDetail(depotHeadArg, token);
        ResponseUtil.resSuccessMsg(response, ExceptionConstant.SERVICE_SUCCESS_MSG);

        return response;
    }

    /**
     * 通过number，票据号获取depotHead及补充信息的列表
     *
     * @param number
     * @return
     */
    @GetMapping("/getDetailByNumber")
    public BaseResponse getDetailByNumber(@RequestParam("number") String number,
                                          @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        List<DepotHeadEx> depotHeadExList = depotHeadService.getDepotHeadExListByNumber(number, token);
        if (depotHeadExList != null && depotHeadExList.size() > 0) {
            ResponseUtil.resSuccess(response, depotHeadExList.get(0));
        }


        return response;
    }

    /**
     * 根据单据号查询关联单据列表
     *
     * @param number
     * @return
     */
    @GetMapping("/getBillListByLinkNumber")
    public BaseResponse getBillListByLinkNumber(@RequestParam("number") String number) {
        BaseResponse response = new BaseResponse();

        ResponseUtil.resSuccess(response, depotHeadService.getListByKey("link_number", number));

        return response;
    }

    /**
     * 修改单据及其子单据信息
     *
     * @param depotHeadBody
     * @param token
     * @return
     */
    @PutMapping("/updateDepotHeadAndDetail")
    public BaseResponse updateDepotHeadAndDetail(@RequestBody DepotHeadExReBody depotHeadBody,
                                                 @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        depotHeadService.updateDepotHeadAndDetail(depotHeadBody, token);
        ResponseUtil.resSuccess(response, null, ExceptionConstant.SERVICE_SUCCESS_MSG);

        return response;
    }

    @PostMapping("/batchSetStatus")
    public BaseResponse batchSetStatus(@RequestBody JSONObject obj,
                                       @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token) {
        BaseResponse response = new BaseResponse();

        String ids = obj.getString("ids");
        String status = obj.getString("status");
        int update = depotHeadService.batchUpdateStatus(ids, status, token);
        ResponseUtil.resSuccessMsg(response, ExceptionConstant.SERVICE_SUCCESS_MSG);

        return response;

    }
}
