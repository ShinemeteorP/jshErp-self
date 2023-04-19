package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.meteor.jsherp.domain.Msg;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.mapper.MsgMapper;
import com.meteor.jsherp.service.MsgService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.service.common.ResourceInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_msg(消息表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
@ResourceInfo("msg")
public class MsgServiceImpl extends CommonServiceImpl<MsgMapper, Msg>
    implements MsgService{

    @Resource
    private MsgMapper msgMapper;

    @Resource
    private UserService userService;

    @Override
    public List<Msg> select(Map<String, String> paramMap) {
        String search = paramMap.get("search");
        JSONObject parse = JSONObject.parseObject(search);
        String name =(String) parse.get("name");
        int currentPage = Integer.parseInt(paramMap.get("currentPage"));
        String pageSizeStr = paramMap.get("pageSize");
        int pageSize = StringUtils.hasText(pageSizeStr) ? Integer.parseInt(pageSizeStr) : 10;
        String token = paramMap.get("token");
        User user = userService.getLoginUser(token);
        if (!"admin".equals(user.getLoginName())){
            QueryWrapper<Msg> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user.getId()).like(StringUtils.hasText(name), "msg_title", name);
            Page<Msg> msgPage = new Page<>(currentPage, pageSize);
            msgMapper.selectPage(msgPage, queryWrapper);
            List<Msg> records = msgPage.getRecords();
            for (Msg m:
                 records) {
                m.setCreateTimeStr();
            }
            return records;
        }
        return null;
    }
}




