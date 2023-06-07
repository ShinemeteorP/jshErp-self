package com.meteor.jsherp.service.impl;

import com.meteor.jsherp.JshErpSelfApplication;
import com.meteor.jsherp.domain.extand.DepotHeadEx;
import com.meteor.jsherp.domain.Msg;
import com.meteor.jsherp.mapper.DepotHeadMapper;
import com.meteor.jsherp.mapper.MaterialMapper;
import com.meteor.jsherp.service.DepotHeadService;
import com.meteor.jsherp.service.MsgService;
import com.meteor.jsherp.service.RedisService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.*;

/**
 * @author 刘鑫
 * @version 1.0
 */
@SpringBootTest(classes = JshErpSelfApplication.class)
@RunWith(SpringRunner.class)
public class RedisServiceImplTest {
    @Resource
    RedisService redisService;

    @Resource
    DepotHeadService depotHeadService;

    @Resource
    MsgService msgService;

    @Resource
    DepotHeadMapper depotHeadMapper;

    @Resource
    MaterialMapper materialMapper;

    @Test
    public void test1(){
        List<Long> idList = Arrays.asList(new Long(260), new Long(261), new Long(262), new Long(263));
        List<Map<String, Object>> materialListMap = materialMapper.getMaterialListMap(idList);
        Assert.assertNotNull(materialListMap);
    }


    @Test
    public void test(){
        List<DepotHeadEx> depotHeadExList = depotHeadMapper.getDepotHeadBoList("其它", "销售订单", new long[]{63}, null, null, null, "", "",
                null, null, "", null, null, null, null, null, null, "", 0, 50);
        Assert.assertNotNull(depotHeadExList);
    }

    @Test
    public void testMsg(){
        HashMap<String, String> map = new HashMap<>();
        map.put("search","{'name':''}");
        map.put("currentPage","1");
        map.put("token","526d86e844cf43479de15ce068ae9ba3_63");
        List<Msg> select = (List<Msg>) msgService.select(map);
    }

}