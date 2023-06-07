package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.meteor.jsherp.domain.MaterialCategory;
import com.meteor.jsherp.domain.extand.BaseTreeNode;
import com.meteor.jsherp.mapper.MaterialCategoryMapper;
import com.meteor.jsherp.service.MaterialCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_material_category(产品类型表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MaterialCategoryServiceImpl extends CommonServiceImpl<MaterialCategoryMapper, MaterialCategory>
    implements MaterialCategoryService {

    @Resource
    private MaterialCategoryMapper materialCategoryMapper;

    @Override
    public List<Long> getIdListByParentId(Long categoryId) {
        ArrayList<Long> idList = new ArrayList<>();
        if(categoryId != null){
            idList.add(categoryId);
            List<MaterialCategory> list = materialCategoryMapper.selectByMap(
                    ImmutableMap.of("parent_id", categoryId));
            if(list != null){
                for (MaterialCategory m:
                     list) {
                    List<Long> list1 = getIdListByParentId(m.getId());
                    idList.addAll(list1);
                }
            }
        }
        return idList;
    }

    @Override
    public List<BaseTreeNode> getMaterialCategoryTree(Long id) {

        return materialCategoryMapper.getMaterialCategoryTree(id);
    }
}




