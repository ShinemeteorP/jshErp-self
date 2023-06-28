package com.meteor.jsherp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

    /**
     * 注册时，新增一个用户对应的userRole数据
     * @param object
     */
    int insertUserBusiness(JSONObject object);

    /**
     * 根据type和keyId，查询UserBusiness对象，并返回其Id
     * @param type
     * @param keyId
     * @return
     */
    Long checkIsValueExist(String type, Long keyId);

    /**
     * 修改用户的按钮权限，
     * @param btnStr 新权限内容
     * @param keyId
     * @param type
     * @return
     */
    int updateBtnStr(String btnStr, Long keyId, String type);

}
