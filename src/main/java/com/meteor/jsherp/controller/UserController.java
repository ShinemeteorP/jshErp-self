package com.meteor.jsherp.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Role;
import com.meteor.jsherp.domain.Tenant;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.extand.UserEx;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.*;
import com.meteor.jsherp.utils.CommonUtil;
import com.meteor.jsherp.utils.RandImageUtil;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @Resource
    private RedisService redisService;

    @Resource
    private UserService userService;

    @Resource
    private TenantService tenantService;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private RoleService roleService;

    /**
     * 用户登入
     * 待优化点：日志写入数据库的功能未实现
     * @param user 用户的参数
     * @param token 请求头中 X-Access-Token 的值
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse login(@RequestBody User user,
                              @RequestHeader(value = ErpAllConstant.REQUEST_TOKEN_KEY, required = false) String token,
                              HttpServletRequest request){
        BaseResponse response = new BaseResponse();
        String loginName = user.getLoginName().trim();
        String password = user.getPassword().trim();
        Map<String, Object> data = null;
        try {
            User loginUser = userService.getLoginUser(token);
            data = new HashMap();
            if (loginUser != null){
                data.put("msgTip", UserConstant.USER_ALREADY_LOGIN);
            }else{
                String msgTip = userService.login(loginName, password);
                data.put("msgTip", msgTip);
                if (UserConstant.USER_CONDITION_FIT.equals(msgTip)) {
                    token = CommonUtil.getToken();
                    User currentUser = userService.userLogin(loginName, password);
                    Tenant tenant = tenantService.getByUserId(currentUser.getTenantId());
                    if (tenant != null){
                        token = token + "_" + tenant.getTenantId();
                    }
                    userService.saveLoginUser(token, currentUser);
                    JSONArray userMenuRole = userBusinessService.getUserMenuRole(currentUser.getId());
                    Role role = roleService.getRoleByUserId(currentUser.getId());
                    String type = role.getType();
                    redisService.insertTokenMap(token, "userNumLimit", tenant.getUserNumLimit() + "");
                    redisService.insertTokenMap(token, "roleType", type);
                    redisService.insertTokenMap(token, "clientIp", CommonUtil.getLocalIp(request));
                    data.put("token", token);
                    data.put("user", currentUser);
                    data.put("userBtn", userMenuRole);
                    data.put("roleType", type);

                }
                ResponseUtil.resSuccess(response, data);

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.customServiceExceptionResponse("用户登陆失败", response);
        }

        return response;
    }

    /**
     * 获取当前用户的租户信息
     * @param request
     * @return
     */
    @GetMapping("/infoWithTenant")
    public BaseResponse getTenant(HttpServletRequest request){
        BaseResponse response = new BaseResponse();
        try {
            String token = request.getHeader(ErpAllConstant.REQUEST_TOKEN_KEY);
            User user = userService.getLoginUser(token);
            long userCount = userService.countNormalUser();
            Tenant tenant = tenantService.getByUserId(user.getTenantId());
            HashMap<Object, Object> map = new HashMap<>();
            map.put("type", tenant.getTenantId());
            map.put("expireTime", CommonUtil.parseDateToStr(tenant.getExpireTime()));
            map.put("userCurrentNum", userCount);
            map.put("userNumLimit", tenant.getUserNumLimit());
            map.put("tenantId", user.getTenantId());
            ResponseUtil.resSuccess(response, map);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }

    /**
     * 获取所有合法用户的id和用户名
     * @return
     */
    @GetMapping("/getUserList")
    public JSONArray getUserList(){
        JSONArray array = new JSONArray();
        try {
            List<User> list = userService.getListByKey(UserConstant.USER_STATUS, UserConstant.USER_STATUS_ALLOW);
            for (User u:
                 list) {
                JSONObject userObj = new JSONObject();
                userObj.put("id", u.getId());
                userObj.put("userName", u.getUsername());
                array.add(userObj);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return array;
    }

    /**
     * 用户退出登入
     * @param token
     * @return
     */
    @GetMapping("/logout")
    public BaseResponse logout(@RequestHeader(value = ErpAllConstant.REQUEST_TOKEN_KEY, required = false) String token){
        BaseResponse response = new BaseResponse();
        try {
            if (StringUtils.hasText(token)) {
                userService.logOut(token);
            }
            ResponseUtil.resSuccess(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.customServiceExceptionResponse("退出失败", response);
        }
        return response;
    }

    /**
     * 获取随机4位数的验证码
     * @param key
     * @return
     */
    @GetMapping("/random/{key}")
    public BaseResponse getRandomCode(@PathVariable("key") String key) {
        BaseResponse response = new BaseResponse();
        HashMap<Object, Object> map = new HashMap<>();
        try {
            String randomCode = CommonUtil.getCharAndNum(4);
            String base64 = RandImageUtil.generate(randomCode);
            map.put("codeNum", randomCode);
            map.put("base64", base64);
            ResponseUtil.resSuccess(response, map);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.customServiceExceptionResponse("获取失败", response);
        }
        return response;
    }


    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping(value = "/registerUser")
    public BaseResponse registerUser(@RequestBody UserEx user){
        BaseResponse response = new BaseResponse();
        try {
            user.setUsername(user.getLoginName());
            userService.checkLoginName(user.getId(), user.getLoginName());
            userService.register(user);
            ResponseUtil.resSuccess(response, "操作成功");
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }
}
