package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meteor.jsherp.domain.extand.UserEx;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_user(用户表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:56
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    /**
     * 工具userName和loginName模糊查找对应的user列表，并且包含了user的角色和组织信息
     * @param userName
     * @param loginName
     * @param start
     * @param pageSize
     * @return
     */
    List<UserEx> selectUserListByUserNameAndLoginName(String userName, String loginName, int start, Integer pageSize);

    Long selectRoleIdByUserEx(Long uId);

    String selectRoleNameByUserEx(Long uId);

    Integer countsByUserNameAndLoginName(String userName, String loginName);

    int getLeaderListByOrgaId(Long orgaId, Long id);
}




