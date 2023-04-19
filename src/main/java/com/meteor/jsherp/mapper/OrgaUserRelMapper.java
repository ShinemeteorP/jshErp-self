package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.OrgaUserRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 刘鑫
* @description 针对表【jsh_orga_user_rel(机构用户关系表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:55
* @Entity generator.domain.OrgaUserRel
*/
public interface OrgaUserRelMapper extends BaseMapper<OrgaUserRel> {

    long[] getUserIdListGroupByOrganization(long userId);
}




