package com.meteor.jsherp.service.common;

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
public class ResourceService<T> {

    @Resource
    private ServiceContainer serviceContainer;

    public List<T> select(String apiName, Map<String, String > paramMap){
        CommonService service = serviceContainer.getService(apiName);
        return service.select(paramMap);
    }



}
