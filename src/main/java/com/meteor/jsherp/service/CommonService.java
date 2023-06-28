package com.meteor.jsherp.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 * @description 该接口有两处作用，1 是为了补充Mybatis-Plus的IService的一些通用表格操作方法方法，统一在此实现，其子类可直接调用
 * 2 是配合自定义的@ResourceInfo注解使用，对特定的一些Service进行基础表格操作集成，统一为ResourceService服务
 * 因此需要声明ResourceService需要的一些方法，并且在实现的抽象类CommonServiceImpl中进行 空实现2 中的一些方法，以免影响1中的其余Service使用
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

    /**
     * 根据所选条件查询对象数据列表
     * @param paramMap
     * @return
     */
    List<? extends T> select(Map<String, String> paramMap);



    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    T selectOne(Long id);

    /**
     * 根据id删除数据
     * @param id
     * @param token
     * @return
     */
    int delete(Long id, String token);
    /**
     * 根据以逗号分割的id列表，批量删除数据
     * @param ids
     * @param token
     * @return
     */
    int deleteBatch(String ids, String token);

    /**
     * 根据条件查询总数量
     * @param parameterMap
     * @return
     */
    Integer counts(Map<String, String> parameterMap);

    /**
     * 根据id和名字查询，不等于该id但名字与与这个相等的记录数量
     * 实现类默认实现了id列名就是id，名字列名就是name的情况，其余情况需要子类自己实现
     * @param id
     * @param name
     * @return
     */
    int checkName(Long id, String name);

    /**
     * 根据对象新增一条记录
     * @param obj
     * @param token
     * @return
     */
    int addObj(JSONObject obj, String token);
    /**
     * 根据对象更新一条记录
     * @param obj
     * @param token
     * @return
     */
    int updateObj(JSONObject obj, String token);
}
