package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.PlatformConfig;
import com.meteor.jsherp.mapper.PlatformConfigMapper;
import com.meteor.jsherp.service.PlatformConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 刘鑫
* @description 针对表【jsh_platform_config(平台参数)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class PlatformConfigServiceImpl extends ServiceImpl<PlatformConfigMapper, PlatformConfig>
    implements PlatformConfigService{

    @Resource
    private PlatformConfigMapper platformConfigMapper;

    @Override
    public PlatformConfig getPlatformConfigByKey(String key) {
        QueryWrapper<PlatformConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ErpAllConstant.PLATFORM_KEY, key);
        PlatformConfig one = platformConfigMapper.selectOne(queryWrapper);
        return one;
    }
}




