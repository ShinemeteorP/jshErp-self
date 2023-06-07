package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.MaterialCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.extand.BaseTreeNode;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_material_category(产品类型表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface MaterialCategoryService extends IService<MaterialCategory>, CommonService<MaterialCategory> {

    /**
     * 根据父级物料id获取其id及所有子孙物料id的列表
     * @param categoryId
     * @return
     */
    List<Long> getIdListByParentId(Long categoryId);

    /**
     * 根据物料父级id，获取其及所有子孙物料信息
     * @param id
     * @return
     */
    List<BaseTreeNode> getMaterialCategoryTree(Long id);
}
