package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.MaterialProperty;
import com.meteor.jsherp.mapper.MaterialPropertyMapper;
import com.meteor.jsherp.service.MaterialPropertyService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_material_property(产品扩展字段表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MaterialPropertyServiceImpl extends ServiceImpl<MaterialPropertyMapper, MaterialProperty>
    implements MaterialPropertyService {

}




