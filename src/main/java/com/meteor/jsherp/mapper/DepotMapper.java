package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.Depot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_depot(仓库表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:54
* @Entity generator.domain.Depot
*/
public interface DepotMapper extends BaseMapper<Depot> {

    List<Long> getDepotIds();
}




