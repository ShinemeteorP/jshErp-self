package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.domain.User;

/**
 * @author 刘鑫
 * @version 1.0
 */
public interface RedisService {
    /**
     * 根据key和token从redis中查询登入用户的相关信息
     * @param token
     * @param key 相关信息的key
     * @return 查询结果
     */
    Object queryMsgByKey(String token, String key);


    /**
     * 在redis存储{token,{k,v}}数据
     * @param token
     * @param key
     * @param value
     */
    void insertTokenMap(String token, String key, Object value);

    /**
     * 删除key对应的数据
     * @param token
     */
    Long delTokenByKey(String token, String key);


}
