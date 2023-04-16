package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONArray;
import com.meteor.jsherp.domain.UserBusiness;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 刘鑫
* @description 针对表【jsh_user_business(用户/角色/模块关系表)】的数据库操作Service
* @createDate 2023-04-13 18:07:56
*/
public interface UserBusinessService extends IService<UserBusiness>, CommonService<UserBusiness> {
    /**
     * 根据用户id返回用户btn对应的url
     * @param userId
     * @return 用户userbiness中的btn的functionId以及对应的url信息
     */
    JSONArray getUserMenuRole(long userId);

    /**
     * 获取userbiness对象
     * @param userId 用户id
     * @param type 类型
     * @return
     */
    UserBusiness getOneByKeyId(long userId, String type);
}
