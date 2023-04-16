package com.meteor.jsherp.service.impl;

import com.meteor.jsherp.JshErpSelfApplication;
import com.meteor.jsherp.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author 刘鑫
 * @version 1.0
 */
@SpringBootTest(classes = JshErpSelfApplication.class)
@RunWith(SpringRunner.class)
public class RedisServiceImplTest {
    @Resource
    RedisService redisService;
    @Test
    public void nullTest(){
        Long aLong = redisService.queryUserId(null);
        System.out.println(aLong);
    }

}