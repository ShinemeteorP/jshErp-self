package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Role;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.RoleMapper;
import com.meteor.jsherp.mapper.UserBusinessMapper;
import com.meteor.jsherp.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 刘鑫
* @description 针对表【jsh_role(角色表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Resource
    private UserBusinessMapper userBusinessMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public Role getRoleByUserId(long userId) {
        QueryWrapper<UserBusiness> queryWrapper = new QueryWrapper<UserBusiness>().eq("key_id", userId).
                eq("type", UserConstant.USER_BUSINESS_USER_ROLE);
        UserBusiness userBusiness = userBusinessMapper.selectOne(queryWrapper);
        String value = userBusiness.getValue();
        if (StringUtils.hasText(value)){
            //遍历value，使用正则表达式，将中括号中的数字取出，存入到longs集合中
            String regex = "(\\[[^\\]]*\\])";
            Pattern compile = Pattern.compile(regex);
            Matcher matcher = compile.matcher(value);
            ArrayList<String> values = new ArrayList<>();
            while (matcher.find()){
                values.add(matcher.group().substring(1, matcher.group().length()-1));
            }
            return roleMapper.selectById(Long.parseLong(values.get(0)));
        }
        return null;
    }
}




