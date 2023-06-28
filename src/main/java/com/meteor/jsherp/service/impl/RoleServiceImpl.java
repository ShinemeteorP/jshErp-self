package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Role;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.RoleMapper;
import com.meteor.jsherp.mapper.UserBusinessMapper;

import com.meteor.jsherp.service.common.ResourceInfo;
import com.meteor.jsherp.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.meteor.jsherp.service.RoleService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 刘鑫
* @description 针对表【jsh_role(角色表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
@ResourceInfo("role")
public class RoleServiceImpl extends CommonServiceImpl<RoleMapper, Role>
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

    @Override
    public List<? extends Role> select(Map<String, String> paramMap) {
        String search = paramMap.get("search");
        JSONObject object = JSONObject.parseObject(search);
        String name = object.getString("name");
        String description = object.getString("description");
        Integer currentPage =Integer.parseInt(paramMap.get("currentPage"));
        Integer pageSize = Integer.parseInt(paramMap.get("pageSize"));
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().like(StringUtils.hasText(name), "name", name).
                like(StringUtils.hasText(description), "description", description);
        Page<Role> page = new Page<>((currentPage - 1) * pageSize, pageSize);
        roleMapper.selectPage(page, queryWrapper);
        return page.getRecords();
    }

    @Override
    public Integer counts(Map<String, String> paramMap) {
        String search = paramMap.get("search");
        JSONObject object = JSONObject.parseObject(search);
        String name = object.getString("name");
        String description = object.getString("description");
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().like(StringUtils.hasText(name), "name", name).
                like(StringUtils.hasText(description), "description", description);
        return roleMapper.selectCount(queryWrapper);
    }

    @Override
    public int addObj(JSONObject obj, String token) {
        Role role = JSONObject.parseObject(obj.toJSONString(), Role.class);
        role.setEnabled(true);
        return roleMapper.insert(role);
    }

    @Override
    public int updateObj(JSONObject obj, String token) {
        Role role = JSONObject.parseObject(obj.toString(), Role.class);
        return roleMapper.updateById(role);
    }

    @Override
    public int delete(Long id, String token) {
        return deleteBatchIds(id.toString(), token);
    }

    @Override
    public int deleteBatch(String ids, String token) {
        return deleteBatchIds(ids, token);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBatchIds(String ids, String token) {
        //日志数据插入
        List<Long> longs = StringUtil.strToLongList(ids);
        return roleMapper.deleteBatchIds(longs);
    }

    @Override
    public int batchSetStatus(String ids, Boolean status) {
        List<Long> longList = StringUtil.strToLongList(ids);
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<Role>().in("id", longList).set("enabled", status);
        int update = roleMapper.update(null, updateWrapper);
        return update;
    }

    @Override
    public List<Role> getAllList() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().eq("enabled", true).orderByAsc("sort").orderByDesc("id");
        return roleMapper.selectList(queryWrapper);
    }
}




