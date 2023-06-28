package com.meteor.jsherp.service.common;

import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Service
public class ResourceService {

    @Resource
    private ServiceContainer serviceContainer;

    public <T> List<T> select(String apiName, Map<String, String > paramMap){
        CommonService service = serviceContainer.getService(apiName);
        return service.select(paramMap);
    }


    public <T> T selectOne(String apiName, Long id) {
        CommonService<T> service = serviceContainer.getService(apiName);
        return service.selectOne(id);
    }

    public <T> int delete(String apiName, Long id, String token) {
        CommonService<T> service = serviceContainer.getService(apiName);
        return service.delete(id, token);
    }

    public <T> int deleteBatch(String apiName, String ids, String token) {
        CommonService<T> service = serviceContainer.getService(apiName);
        return service.deleteBatch(ids, token);
    }

    public Integer count(String apiName, Map<String, String> parameterMap) {
        CommonService service = serviceContainer.getService(apiName);
        return service.counts(parameterMap);
    }

    public int checkIsNameExist(String apiName, Long id, String name) {
        CommonService service = serviceContainer.getService(apiName);
        return service.checkName(id, name);
    }

    public int addObj(String apiName, JSONObject obj, String token) {
        CommonService service = serviceContainer.getService(apiName);
        return service.addObj(obj, token);
    }

    public int updateObj(String apiName, JSONObject obj, String token) {
        CommonService service = serviceContainer.getService(apiName);
        return service.updateObj(obj, token);
    }
}
