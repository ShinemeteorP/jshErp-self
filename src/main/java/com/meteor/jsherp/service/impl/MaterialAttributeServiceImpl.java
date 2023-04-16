package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.MaterialAttribute;
import com.meteor.jsherp.mapper.MaterialAttributeMapper;
import com.meteor.jsherp.service.MaterialAttributeService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_material_attribute(产品属性表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MaterialAttributeServiceImpl extends ServiceImpl<MaterialAttributeMapper, MaterialAttribute>
    implements MaterialAttributeService {

}




