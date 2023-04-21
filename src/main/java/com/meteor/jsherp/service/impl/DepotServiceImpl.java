package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.Depot;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.DepotMapper;
import com.meteor.jsherp.service.DepotService;
import com.meteor.jsherp.service.UserBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_depot(仓库表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class DepotServiceImpl extends ServiceImpl<DepotMapper, Depot>
    implements DepotService {

    @Resource
    private DepotMapper depotMapper;

    @Resource
    private UserBusinessService userBusinessService;

    @Override
    public JSONArray findDepotByUserId(Long id, String depotFlag) {
        JSONArray array = new JSONArray();
        List<Depot> depotList = getAllDepotList();
        if (depotList != null){
            if (BusinessConstant.SYSTEM_CONFIG_APPROVAL_OPEN.equals(depotFlag)){
                UserBusiness userBusiness = userBusinessService.getOneByKeyMap(
                        ImmutableMap.of("key_id", id, "type", UserConstant.USER_BUSINESS_USER_DEPOT));
                String value = userBusiness.getValue();
                for (Depot d:
                     depotList) {
                    if (value.contains("[" + d.getId() + "]")){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", d.getId());
                        jsonObject.put("depotName", d.getName());
                        jsonObject.put("isDefault", d.getIsDefault());
                        array.add(jsonObject);
                    }
                }
            }else {
                for (Depot d:
                     depotList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", d.getId());
                    jsonObject.put("depotName", d.getName());
                    jsonObject.put("isDefault", d.getIsDefault());
                    array.add(jsonObject);
                }
            }
        }
        return array;
    }

    @Override
    public List<Depot> getAllDepotList() {
        QueryWrapper<Depot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 0).eq("enabled", true).orderByAsc("sort").orderByDesc("id");
        List<Depot> depots = depotMapper.selectList(queryWrapper);
        return depots;
    }

    @Override
    public List<Long> getDepotIds(Long id, String depotFlag) {
        List<Long> idArray = depotMapper.getDepotIds();
        List<Long> res = new LinkedList<Long>();
        if (BusinessConstant.SYSTEM_CONFIG_APPROVAL_OPEN.equals(depotFlag)){
            UserBusiness userBusiness = userBusinessService.getOneByKeyMap(
                    ImmutableMap.of("key_id", id, "type", UserConstant.USER_BUSINESS_USER_DEPOT));
            String value = userBusiness.getValue();
            if (value == null){
                return null;
            }
            for (Long i:
                 idArray) {
                if ( value.contains("[" + i + "]")){
                    res.add(i);
                }
            }
        }else {
            res = idArray;
        }
        return res;
    }
}




