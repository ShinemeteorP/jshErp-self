package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    public Object queryMsgByKey(String token, String key){
        if(!StringUtils.hasText(token)){
            return null;
        }

        return redisTemplate.opsForHash().get(token, key);
    }


    @Override
    public void insertTokenMap(String token, String key, Object value) {
        redisTemplate.opsForHash().put(token, key, value);
    }

    @Override
    public Long delTokenByKey(String token, String key) {
        return redisTemplate.opsForHash().delete(token, key);
    }

}
