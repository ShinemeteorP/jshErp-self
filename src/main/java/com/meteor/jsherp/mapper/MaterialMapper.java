package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.Material;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_material(产品表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:55
* @Entity generator.domain.Material
*/
public interface MaterialMapper extends BaseMapper<Material> {

    @MapKey("id")
    List<Map<String , Object>> getMaterialListMap(List<Long> idList);
}




