package com.meteor.jsherp.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Function;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.FunctionMapper;
import com.meteor.jsherp.mapper.UserBusinessMapper;
import com.meteor.jsherp.service.UserBusinessService;
import org.springframework.stereotype.Service;
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
public class UserBusinessServiceImpl extends CommonServiceImpl<UserBusinessMapper, UserBusiness>
    implements UserBusinessService{

    @Resource
    private UserBusinessMapper userBusinessMapper;

    @Resource
    private FunctionMapper functionMapper;

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
        Long tenantId = object.getLong("tenantId");
        UserBusiness business = new UserBusiness();
        business.setType(type);
        business.setKeyId(keyId);
        business.setTenantId(tenantId);
        String newValue = value.replaceAll(",", "\\]\\[");
        newValue = newValue.replaceAll("\\[0\\]","").replaceAll("\\[\\]","");
        business.setValue(newValue);
        int insert = userBusinessMapper.insert(business);
        //日志插入
        return insert;
    }
}




