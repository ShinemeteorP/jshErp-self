package com.meteor.jsherp.controller;

import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.DepotHeadService;
import com.meteor.jsherp.service.OrgaUserRelService;
import com.meteor.jsherp.service.RedisService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/depotHead")
@RestController
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

    @GetMapping("/getBuyAndSaleStatistics")
    public BaseResponse getBuyAndSaleStatistics(@RequestParam(value = "roleType", required = false) String type,
                                                @RequestHeader(value = ErpAllConstant.REQUEST_TOKEN_KEY, required = false) String token){
        BaseResponse response = new BaseResponse();
        try {
            String roleType = (String) redisService.queryMsgByKey(token, "roleType");
            Long userId = userService.getLoginUser(token).getId();
            long[] userIds = orgaUserRelService.getUserIdListByRole(userId, roleType);
            String approvalFlag = systemConfigController.getCurrent().getMultiLevelApprovalFlag();
            Map<String, Object> statistics = depotHeadService.getBuyAndSaleStatistics(userIds, userId, approvalFlag);
            ResponseUtil.resSuccess(response, statistics);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }
}
