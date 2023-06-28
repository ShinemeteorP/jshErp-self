package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_role(角色表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface RoleService extends IService<Role>, CommonService<Role> {

    /**
     * 根据用户id获取对应的role对象
     * @param userId 用户id
     * @return
     */
    Role getRoleByUserId(long userId);


    @Transactional(rollbackFor = Exception.class)
    int deleteBatchIds(String ids, String token);

    /**
     * 批量修改状态，id在id的列表中
     * @param ids id列表
     * @param status 修改后的状态
     * @return
     */
    int batchSetStatus(String ids, Boolean status);

    /**
     * 获取目前租户下所有未删除且有效的角色对象列表
     * @return
     */
    List<Role> getAllList();
}
