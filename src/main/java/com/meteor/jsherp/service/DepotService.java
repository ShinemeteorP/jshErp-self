package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.domain.Depot;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_depot(仓库表)】的数据库操作Service
* @createDate 2023-04-13 18:07:54
*/
public interface DepotService extends IService<Depot> {

    /**
     * 根据用户id获取对应的仓库数据
     * @param id 用户id
     * @return
     */
    JSONArray findDepotByUserId(Long id, String depotFlag);

    /**
     * 获取type为 0 的所有仓库列表
     * @return
     */
    List<Depot> getAllDepotList();

    /**
     * 根据用户id获取对应的仓库id列表
     * @param id 用户id
     * @param depotFlag 仓库开启标识
     * @return
     */
    List<Long> getDepotIds(Long id, String depotFlag);
}
