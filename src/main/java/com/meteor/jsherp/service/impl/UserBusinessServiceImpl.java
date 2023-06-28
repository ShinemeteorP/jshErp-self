package com.meteor.jsherp.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Function;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.FunctionMapper;
import com.meteor.jsherp.mapper.UserBusinessMapper;
import com.meteor.jsherp.service.LogService;
import com.meteor.jsherp.service.UserBusinessService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.service.common.ResourceInfo;
import com.meteor.jsherp.utils.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 刘鑫
* @description 针对表【jsh_user_business(用户/角色/模块关系表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
@ResourceInfo("userBusiness")
public class UserBusinessServiceImpl extends CommonServiceImpl<UserBusinessMapper, UserBusiness>
    implements UserBusinessService{

    @Resource
    private UserBusinessMapper userBusinessMapper;

    @Resource
    private FunctionMapper functionMapper;

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @Override
    public JSONArray getUserMenuRole(long userId) {
        JSONArray array = new JSONArray();
        JSONArray btnArray = new JSONArray();
        QueryWrapper<UserBusiness> queryWrapper = new QueryWrapper<UserBusiness>().eq("key_id", userId).eq("type", UserConstant.USER_BUSINESS_USER_ROLE);
        UserBusiness userBusiness = userBusinessMapper.selectOne(queryWrapper);
        String value = userBusiness.getValue();
        if(StringUtils.hasText(value) && value.indexOf("[") != -1 && value.indexOf("]") != -1){
            value = value.replace("[","").replace("]","");
            queryWrapper = new QueryWrapper<UserBusiness>().eq("key_id", value).eq("type", UserConstant.USER_BUSINESS_ROLE_FUNCTION);
            UserBusiness functionUserBusiness = userBusinessMapper.selectOne(queryWrapper);
            if(StringUtils.hasText(functionUserBusiness.getBtnStr())){
                btnArray = JSONArray.parseArray(functionUserBusiness.getBtnStr());
            }
        }
        for (Object btn:
             btnArray) {
            JSONObject jsonObject = JSONObject.parseObject(btn.toString());
            String funId = jsonObject.getString("funId");
            Function function = functionMapper.selectById(Long.parseLong(funId));
            JSONObject btnObject = new JSONObject();
            btnObject.put("url", function != null? function.getUrl() : null);
            btnObject.put("btnStr", jsonObject.getString("btnStr"));
            array.add(btnObject);
        }
        return array;
    }

    @Override
    public UserBusiness getOneByKeyId(long userId, String type) {
        QueryWrapper<UserBusiness> eq = new QueryWrapper<UserBusiness>().eq("key_id", userId).eq("type", type);
        return userBusinessMapper.selectOne(eq);
    }

    @Override
    public int insertUserBusiness(JSONObject object) {

        String value = object.getString("value");
        String type = object.getString("type");
        String keyId = object.getString("keyId");
//        Long tenantId = object.getLong("tenantId");
        UserBusiness business = new UserBusiness();
        business.setType(type);
        business.setKeyId(keyId);
//        business.setTenantId(tenantId);
        String newValue = value.replaceAll(",", "\\]\\[");
        newValue = newValue.replaceAll("\\[0\\]","").replaceAll("\\[\\]","");
        business.setValue(newValue);
        int insert = userBusinessMapper.insert(business);
        //日志插入
        return insert;
    }

    @Override
    public Long checkIsValueExist(String type, Long keyId) {
        QueryWrapper<UserBusiness> wrapper = new QueryWrapper<UserBusiness>().eq("type", type).eq("key_id", keyId);
        UserBusiness userBusiness = userBusinessMapper.selectOne(wrapper);
        if (userBusiness != null){
            return userBusiness.getId();
        }
        return null;
    }

    @Override
    public int updateBtnStr(String btnStr, Long keyId, String type) {
        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setKeyId(keyId.toString());
        UpdateWrapper<UserBusiness> set = new UpdateWrapper<UserBusiness>().eq("key_id", keyId).eq("type", type).set("btn_str", btnStr);
        int update = userBusinessMapper.update(null, set);
        return update;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addObj(JSONObject obj, String token) {
        UserBusiness userBusiness = JSONObject.parseObject(obj.toString(), UserBusiness.class);
//        Long tenantId = CommonUtil.getTenantIdByToken(token);
//
//        if (tenantId != 0L) {
//            userBusiness.setTenantId(tenantId);
//        }
        userBusiness.setValue(parseUserBusinessValue(userBusiness));
        int insert = userBusinessMapper.insert(userBusiness);
        //日志数据插入
        logService.insertLog("用户权限", "新增 id 为 " + userBusiness.getId() + " userBusiness对象", 1);
        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateObj(JSONObject obj, String token) {
        UserBusiness userBusiness = JSONObject.parseObject(obj.toString(), UserBusiness.class);
        userBusiness.setValue(parseUserBusinessValue(userBusiness));
        int update = userBusinessMapper.updateById(userBusiness);
        //日志数据插入
        logService.insertLog("用户权限", "修改 id 为 " + userBusiness.getId() + " userBusiness对象", 1);
        return update;
    }

    private String parseUserBusinessValue(UserBusiness userBusiness){
        String value = userBusiness.getValue();
        String newValue = value.replaceAll(",", "\\]\\[");
        newValue = newValue.replaceAll("\\[0\\]", "").replaceAll("\\[\\]", "");
        return newValue;
    }
}




