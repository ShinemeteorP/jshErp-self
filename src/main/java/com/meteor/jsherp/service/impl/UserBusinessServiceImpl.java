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
        QueryWrapper<UserBusiness> queryWrapper = new QueryWrapper<UserBusiness>().eq("key_id", userId).eq("type", UserConstant.USER_BUSINESS_USER_ROLE);
        UserBusiness userBusiness = userBusinessMapper.selectOne(queryWrapper);
        String btnStr = userBusiness.getBtnStr();
        if (StringUtils.hasText(btnStr)){
            //遍历btnStr，使用正则表达式，将中括号中的数字取出，存入到longs集合中
            String regex = "(\\[[^\\]]*\\])";
            Pattern compile = Pattern.compile(regex);
            Matcher matcher = compile.matcher(btnStr);
            ArrayList<String> funIds = new ArrayList<>();
            while (matcher.find()){
                funIds.add(matcher.group().substring(1, matcher.group().length()-1));
            }

            for (int i = 0; i < funIds.size(); i++) {
                Function function = functionMapper.selectById(Long.parseLong(funIds.get(i)));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put( funIds.get(i), function.getUrl());
                array.add(jsonObject);
            }

        }
        return array;
    }

    @Override
    public UserBusiness getOneByKeyId(long userId, String type) {
        QueryWrapper<UserBusiness> eq = new QueryWrapper<UserBusiness>().eq("key_id", userId).eq("type", type);
        return userBusinessMapper.selectOne(eq);
    }
}




