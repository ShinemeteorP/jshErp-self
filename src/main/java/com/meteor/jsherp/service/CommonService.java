package com.meteor.jsherp.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 */
public interface CommonService<T> {
    /**
     * 根据某一列获取多条记录
     * @param key 列名
     * @param value 值
     * @return
     */
    List<T> getListByKey(String key, Object value);

    /**
     * 根据多列获取多条记录
     * @param map 存储列名与对应的值
     * @return
     */
    List<T> getListByKeyMap(Map<String, Object> map);

    /**
     * 根据某一列获取一条记录
     * @param key 列名
     * @param value 值
     * @return
     */
    T getOneByKey(String key, Object value);
    /**
     * 根据多列获取一条记录
     * @param map 存储列名与对应的值
     * @return
     */
    T getOneByKeyMap(Map<String, Object> map);

    List<? extends T> select(Map<String, String> paramMap);
}
