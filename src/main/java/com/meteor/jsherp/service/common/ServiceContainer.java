package com.meteor.jsherp.service.common;

/**
 * @author 刘鑫
 * @version 1.0
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.service.CommonService;
import com.meteor.jsherp.service.impl.CommonServiceImpl;
import com.meteor.jsherp.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 需要进行通用数据操作的 Service 容器，以service名字key，对象为value
 */
@Component
public class ServiceContainer {

    private Map<String, CommonService> container = new HashMap<>();

    @Autowired(required = false)
    public synchronized void init(CommonService[] services){
        for (CommonService service:
             services) {
            Class<? extends CommonService> aClass = service.getClass();
            ResourceInfo annotation = CommonUtil.getAnnotation(aClass, ResourceInfo.class);
            if (annotation != null) {
                container.put(annotation.value(), service);
            }
        }
    }

    public CommonService getService(String apiName){
        return container.get(apiName);
    }
}
