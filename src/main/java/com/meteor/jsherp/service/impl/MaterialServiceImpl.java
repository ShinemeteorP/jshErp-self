package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Material;
import com.meteor.jsherp.mapper.MaterialMapper;
import com.meteor.jsherp.service.MaterialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_material(产品表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MaterialServiceImpl extends CommonServiceImpl<MaterialMapper, Material>
    implements MaterialService{

    @Resource
    private MaterialMapper materialMapper;

    @Override
    public Map<Long, String> getMaterialListMap(ArrayList<Long> idList) {
        HashMap<Long, String> materialListMap = new HashMap<>();
        List<Map<String, Object>> materialListMapRes = materialMapper.getMaterialListMap(idList);
        for (Map<String, Object> map:
             materialListMapRes) {
            materialListMap.put((Long) map.get("id"),(String) map.get("materialList"));
        }
        return materialListMap;
    }
}




