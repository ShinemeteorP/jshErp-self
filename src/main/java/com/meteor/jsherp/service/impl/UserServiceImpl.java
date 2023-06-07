package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Tenant;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.extand.UserEx;
import com.meteor.jsherp.mapper.TenantMapper;
import com.meteor.jsherp.mapper.UserMapper;
import com.meteor.jsherp.service.TenantService;
import com.meteor.jsherp.service.UserBusinessService;
import com.meteor.jsherp.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
* @author 刘鑫
* @description 针对表【jsh_user(用户表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User>
    implements UserService{

    //租户对应的默认权限
    @Value("${manage.roleId}")
    private Long roleId;

    private ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();

    @Resource
    private UserMapper userMapper;

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private TenantService tenantService;

    @Override
    public User userLogin(String loginName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserConstant.USER_LOGIN_NAME, loginName)
                .eq(UserConstant.USER_PASSWORD, password)
                .eq(UserConstant.USER_STATUS, UserConstant.USER_STATUS_ALLOW);

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public String login(String loginName, String password) throws Exception{
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(UserConstant.USER_LOGIN_NAME, loginName);
            User user= userMapper.selectOne(queryWrapper);
            if (user == null){
                return UserConstant.LOGIN_NAME_NOT_EXIST;
            }else{
                if (user.getStatus() != UserConstant.USER_STATUS_ALLOW){
                    return UserConstant.BLACK_USER;
                }
                QueryWrapper<Tenant> query = new QueryWrapper<Tenant>().eq("tenant_id", user.getTenantId());
                Tenant tenant = tenantMapper.selectOne(query);
                if (!tenant.getEnabled()){
                    return UserConstant.BLACK_TENANT;
                }
                if (tenant.getExpireTime().compareTo(new Date()) < 0){
                    return UserConstant.EXPIRE_TENANT;
                }
                if (userLogin(loginName, password) == null){
                    return UserConstant.USER_PASSWORD_ERROR;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return UserConstant.USER_ACCESS_EXCEPTION;
        }

        return UserConstant.USER_CONDITION_FIT;
    }

    @Override
    public long countNormalUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq(UserConstant.USER_STATUS, UserConstant.USER_STATUS_ALLOW);

        return userMapper.selectCount(queryWrapper);
    }

    @Override
    public void saveLoginUser(String token, User currentUser) {
        userMap.put(token, currentUser);
    }

    @Override
    public User getLoginUser(String token){
        if (StringUtils.hasText(token)){
            return userMap.get(token);
        }
        return null;
    }

    @Override
    public void logOut(String token) {
        if (StringUtils.hasText(token)){
            userMap.remove(token);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkLoginName(Long id, String loginName) {
        if(StringUtils.hasText(loginName)){
            QueryWrapper<User> wrapper = new QueryWrapper<User>().eq(UserConstant.USER_LOGIN_NAME, loginName);
            List<User> userList = userMapper.selectList(wrapper);
            if (userList != null && userList.size() > 0) {
                if(userList.size() > 1){
                    //异常日志写入并抛出异常
                }else if(userList.size() == 1){
                    if(id == null || (id != null && id != userList.get(0).getId())){
                        //异常日志写入并抛出异常
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserEx user) {
        if("admin".equals(user.getUsername())){
            //写入错误日志并报业务错误
        }
        //表明用户不是系统自带的
        user.setIsystem(UserConstant.USER_NOT_SYSTEM);
        if(user.getIsmanager() == null){
            //非管理员
            user.setIsmanager(UserConstant.USER_NOT_MANAGER);
        }
        user.setStatus(UserConstant.USER_STATUS_ALLOW);
        userMapper.insert(user);
        user.setTenantId(user.getId());
        JSONObject object = new JSONObject();
        object.put("type", UserConstant.USER_BUSINESS_USER_ROLE);
        object.put("keyId", user.getId());
        object.put("tenantId", user.getTenantId());
        JSONArray array = new JSONArray();
        array.add(roleId);
        object.put("value", array.toString());
        userBusinessService.insertUserBusiness(object);
        JSONObject tenant = new JSONObject();
        tenant.put("tenantId",user.getTenantId());
        tenant.put("loginName",user.getLoginName());
        tenant.put("userNumLimit",user.getUserNumLimit());
        tenant.put("expireTime",user.getExpireTime());
        tenant.put("remark",user.getRemark());
        tenantService.insertTenant(tenant);
    }
}




