package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.SystemConfig;
import com.meteor.jsherp.mapper.SystemConfigMapper;
import com.meteor.jsherp.service.SystemConfigService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_system_config(系统参数)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
public class SystemConfigServiceImpl extends CommonServiceImpl<SystemConfigMapper, SystemConfig>
    implements SystemConfigService{

}




