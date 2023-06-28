package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.service.CommonService;
import com.meteor.jsherp.service.common.ServiceContainer;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 刘鑫
 * @version 1.0
 */


public abstract class CommonServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements CommonService<T> {


    @Override
    public List<T> getListByKey(String key, Object value){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(value != null, key, value);
        List<T> ts = super.baseMapper.selectList(queryWrapper);
        return ts;
    }

    @Override
    public List<T> getListByKeyMap(Map<String, Object> map){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> e:
             entries) {
            queryWrapper.eq(e.getValue() != null,e.getKey(), e.getValue());
        }
        List<T> ts = super.baseMapper.selectList(queryWrapper);
        return ts;
    }

    @Override
    public T getOneByKey(String key, Object value) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(value != null, key, value);
        return super.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public T getOneByKeyMap(Map<String, Object> map) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> e:
                entries) {
            queryWrapper.eq(e.getValue() != null, e.getKey(), e.getValue());
        }
        return super.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<? extends T> select(Map<String, String> paramMap) {
        return null;
    }

    @Override
    public T selectOne(Long id) {
        return super.baseMapper.selectById(id);
    }

    @Override
    public int delete(Long id, String token) {
        return 0;
    }

    @Override
    public int deleteBatch(String ids, String token) {
        return 0;
    }

    @Override
    public Integer counts(Map<String, String> parameterMap) {
        return null;
    }

    @Override
    public int checkName(Long id, String name) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>().ne(id != null, "id", id).eq(name != null, "name", name);
        return super.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int addObj(JSONObject obj, String token) {
        return 0;
    }

    @Override
    public int updateObj(JSONObject obj, String token) {
        return 0;
    }
}
