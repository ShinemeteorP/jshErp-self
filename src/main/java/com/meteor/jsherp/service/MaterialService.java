package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Material;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_material(产品表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface MaterialService extends IService<Material>, CommonService<Material> {

    /**
     * 获取对应id的订单的物料属性类别集合
     * @param idList 订单id
     * @return
     */
    Map<Long, String> getMaterialListMap(ArrayList<Long> idList);
}
