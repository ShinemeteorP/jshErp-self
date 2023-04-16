package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.MaterialCategory;
import com.meteor.jsherp.mapper.MaterialCategoryMapper;
import com.meteor.jsherp.service.MaterialCategoryService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_material_category(产品类型表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MaterialCategoryServiceImpl extends ServiceImpl<MaterialCategoryMapper, MaterialCategory>
    implements MaterialCategoryService {

}




