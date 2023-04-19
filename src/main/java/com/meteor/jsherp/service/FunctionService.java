package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.domain.Function;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.UserBusiness;

import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_function(功能模块表)】的数据库操作Service
* @createDate 2023-04-13 18:07:54
*/
public interface FunctionService extends IService<Function> {
    /**
     * 获取用户的不同级别菜单权限信息
     * @param userBusiness 用户拥有的菜单权限
     * @param functionTree 父菜单编号及子菜单列表的map
     * @param pNumber 初始父菜单编号
     * @param approvalFlag 是否开启多级审核，true 开启 false 关闭
     * @return
     */
    JSONArray getMenuArray(UserBusiness userBusiness, String pNumber,  Map<String,  List<Function>> functionTree, String approvalFlag);


    /**
     * 通过parent_number获取所有子菜单
     * @param pNumber
     * @return
     */
    List<Function> getListByParent(String pNumber);
}
