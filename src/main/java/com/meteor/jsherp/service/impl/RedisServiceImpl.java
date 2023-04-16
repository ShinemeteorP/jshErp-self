package com.meteor.jsherp.service.impl;

import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
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

        return redisTemplate.opsForHash().get(token, key);
    }

    @Override
    public Long queryUserId(String token) {
        if(!StringUtils.hasText(token)){
            return null;
        }
        Long id = (Long) queryMsgByKey(token, UserConstant.CURRENT_USER_ID);
        if (id != null) {
            redisTemplate.expire(id, 86400, TimeUnit.SECONDS);
        }
        return id;

    }

    @Override
    public void insertTokenMap(String token, String key, String value) {
        redisTemplate.opsForHash().put(token, key, value);
    }

    @Override
    public Long delTokenByKey(String token, String key) {
        return redisTemplate.opsForHash().delete(token, key);
    }
}
