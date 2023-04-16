package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.PlatformConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 刘鑫
* @description 针对表【jsh_platform_config(平台参数)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface PlatformConfigService extends IService<PlatformConfig> {


    PlatformConfig getPlatformConfigByKey(String key);

}
