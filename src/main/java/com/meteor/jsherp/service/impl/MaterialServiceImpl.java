package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Material;
import com.meteor.jsherp.mapper.MaterialMapper;
import com.meteor.jsherp.service.MaterialService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_material(产品表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material>
    implements MaterialService{

}




