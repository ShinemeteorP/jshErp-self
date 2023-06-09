package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.MaterialCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meteor.jsherp.domain.extand.BaseTreeNode;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_material_category(产品类型表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:55
* @Entity generator.domain.MaterialCategory
*/
public interface MaterialCategoryMapper extends BaseMapper<MaterialCategory> {


    List<BaseTreeNode> getMaterialCategoryTree(Long id);

    List<BaseTreeNode> getNextNodeTree(Long id, Long currentId);
}




