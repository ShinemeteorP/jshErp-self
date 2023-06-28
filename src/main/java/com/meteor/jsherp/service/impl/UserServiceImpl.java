package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.OrgaUserRel;
import com.meteor.jsherp.domain.Tenant;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.extand.UserEx;
import com.meteor.jsherp.exception.BusinessException;
import com.meteor.jsherp.mapper.TenantMapper;
import com.meteor.jsherp.mapper.UserMapper;
import com.meteor.jsherp.service.*;
import com.meteor.jsherp.service.common.ResourceInfo;
import com.meteor.jsherp.utils.CommonUtil;
import com.meteor.jsherp.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.prefs.BackingStoreException;

/**
* @author 刘鑫
* @description 针对表【jsh_user(用户表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
@Slf4j
@ResourceInfo("user")
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User>
    implements UserService{

    //租户对应的默认权限
    @Value("${manage.roleId}")
    private Long roleId;

    private ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();

    @Resource
    private RedisService redisService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private TenantService tenantService;

    @Resource
    private LogService logService;

    @Resource
    private OrgaUserRelService orgaUserRelService;

    @Override
    public User userLogin(String loginName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserConstant.USER_LOGIN_NAME, loginName)
                .eq(UserConstant.USER_PASSWORD, password)
                .eq(UserConstant.USER_STATUS, UserConstant.USER_STATUS_ALLOW);

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public String login(String loginName, String password){
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(UserConstant.USER_LOGIN_NAME, loginName);
            User user= userMapper.selectOne(queryWrapper);
            if (user == null){
                return UserConstant.LOGIN_NAME_NOT_EXIST;
            }else{
                if (user.getStatus() != UserConstant.USER_STATUS_ALLOW){
                    return UserConstant.BLACK_USER;
                }
                QueryWrapper<Tenant> query = new QueryWrapper<Tenant>().eq("tenant_id", user.getTenantId());
                Tenant tenant = tenantMapper.selectOne(query);
                if (!tenant.getEnabled()){
                    return UserConstant.BLACK_TENANT;
                }
                if (tenant.getExpireTime().compareTo(new Date()) < 0){
                    return UserConstant.EXPIRE_TENANT;
                }
                if (userLogin(loginName, password) == null){
                    return UserConstant.USER_PASSWORD_ERROR;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return UserConstant.USER_ACCESS_EXCEPTION;
        }

        return UserConstant.USER_CONDITION_FIT;
    }

    @Override
    public long countNormalUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq(UserConstant.USER_STATUS, UserConstant.USER_STATUS_ALLOW);

        return userMapper.selectCount(queryWrapper);
    }

    @Override
    public void saveLoginUser(String token, User currentUser) {
        userMap.put(token, currentUser);
    }

    @Override
    public User getLoginUser(String token){
        if (StringUtils.hasText(token)){
            return userMap.get(token);
        }
        return null;
    }

    @Override
    public void logOut(String token) {
        if (StringUtils.hasText(token)){
            userMap.remove(token);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkLoginName(Long id, String loginName) {
        if(StringUtils.hasText(loginName)){
            QueryWrapper<User> wrapper = new QueryWrapper<User>().eq(UserConstant.USER_LOGIN_NAME, loginName);
            List<User> userList = userMapper.selectList(wrapper);
            if (userList != null && userList.size() > 0) {
                //新增时用户名已存在
                if(userList.size() > 1){
                    //异常日志写入并抛出异常
                    throw new BusinessException(ExceptionConstant.USER_LOGIN_NAME_ALREADY_EXISTS_CODE, ExceptionConstant.USER_LOGIN_NAME_ALREADY_EXISTS_MSG);
                //修改时，该用户名在数据库里的id与服务器传输过来的id不一致
                }else if(userList.size() == 1){
                    if(id == null || (id != null && !id.equals(userList.get(0).getId()))){
                        //异常日志写入并抛出异常
                        throw new BusinessException(ExceptionConstant.USER_LOGIN_NAME_ALREADY_EXISTS_CODE, ExceptionConstant.USER_LOGIN_NAME_ALREADY_EXISTS_MSG);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserEx user) {
        if("admin".equals(user.getUsername())){
            //写入错误日志并报业务错误
            throw new BusinessException(ExceptionConstant.USER_NAME_LIMIT_USE_CODE, ExceptionConstant.USER_NAME_LIMIT_USE_MSG);
        }
        //表明用户不是系统自带的
        user.setIsystem(UserConstant.USER_NOT_SYSTEM);
        if(user.getIsmanager() == null){
            //非管理员
            user.setIsmanager(UserConstant.USER_NOT_MANAGER);
        }
        user.setStatus(UserConstant.USER_STATUS_ALLOW);
        userMapper.insert(user);
        user.setTenantId(user.getId());
        JSONObject object = new JSONObject();
        object.put("type", UserConstant.USER_BUSINESS_USER_ROLE);
        object.put("keyId", user.getId());
        object.put("tenantId", user.getTenantId());
        JSONArray array = new JSONArray();
        array.add(roleId);
        object.put("value", array.toString());
        userBusinessService.insertUserBusiness(object);
        JSONObject tenant = new JSONObject();
        tenant.put("tenantId",user.getTenantId());
        tenant.put("loginName",user.getLoginName());
        tenant.put("userNumLimit",user.getUserNumLimit());
        tenant.put("expireTime",user.getExpireTime());
        tenant.put("remark",user.getRemark());
        tenantService.insertTenant(tenant);
        log.info("===========创建租户完成=============");
        logService.insertLog("用户", "用户名为 " + user.getLoginName() + " 注册成功", 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(JSONObject obj, String token) throws NoSuchAlgorithmException {
        long userNumLimit = Long.parseLong(redisService.queryMsgByKey(token, "userNumLimit").toString());
        long count = countNormalUser();
        if(count > userNumLimit){
            throw new BusinessException(ExceptionConstant.USER_OVER_LIMIT_FAILED_CODE, ExceptionConstant.USER_OVER_LIMIT_FAILED_MSG);
        }
        UserEx userEx = JSONObject.parseObject(obj.toJSONString(), UserEx.class);
        if("admin".equals(userEx.getLoginName())){
            throw new BusinessException(ExceptionConstant.USER_NAME_LIMIT_USE_CODE, ExceptionConstant.USER_NAME_LIMIT_USE_MSG);
        }

        checkLoginName(userEx.getId(), userEx.getLoginName());
        UserEx user = addUserWithDefault(userEx);
        if(user == null){
            throw new BusinessException(ExceptionConstant.USER_ADD_FAILED_CODE, ExceptionConstant.USER_ADD_FAILED_MSG);
        }
        //新增用户的UserRole数据
        if(user.getRoleId() != null){
            JSONObject object = new JSONObject();
            object.put("type", UserConstant.USER_BUSINESS_USER_ROLE);
            object.put("key_id", user.getId());
            object.put("value", "[" + user.getRoleId() + "]");
            userBusinessService.addObj(object, token);
        }
        //如果用户没有组织直接跳过，返回
        if(user.getOrgaId() == null){
            return;
        }
        if("1".equals(user.getLeaderFlag())){
            int leaderCount = userMapper.getLeaderListByOrgaId(user.getOrgaId(), user.getId());
            if(leaderCount > 0){
                throw new BusinessException(ExceptionConstant.USER_LEADER_IS_EXIST_CODE, ExceptionConstant.USER_LEADER_IS_EXIST_MSG);
            }
        }
        OrgaUserRel orgaUserRel = new OrgaUserRel();
        orgaUserRel.setOrgaId(user.getOrgaId());
        orgaUserRel.setUserId(user.getId());
        orgaUserRel.setUserBlngOrgaDsplSeq(user.getUserBlngOrgaDsplSeq());
        int rows = orgaUserRelService.addOrgaUserRel(orgaUserRel, token);
        if(rows < 1){
            throw new BusinessException(ExceptionConstant.ORGA_USER_REL_ADD_FAILED_CODE, ExceptionConstant.ORGA_USER_REL_ADD_FAILED_MSG);
        }
        logService.insertLog("用户", "新增用户" + user.getLoginName() + "成功", 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(JSONObject obj, String token) {
        UserEx userEx = JSONObject.parseObject(obj.toJSONString(), UserEx.class);
        if("admin".equals(userEx.getLoginName())){
            throw new BusinessException(ExceptionConstant.USER_NAME_LIMIT_USE_CODE, ExceptionConstant.USER_NAME_LIMIT_USE_MSG);
        }

        checkLoginName(userEx.getId(), userEx.getLoginName());
        int updateById = userMapper.updateById(userEx);
        if(updateById < 1){
            throw new BusinessException(ExceptionConstant.USER_ADD_FAILED_CODE, ExceptionConstant.USER_ADD_FAILED_MSG);
        }
        //新增用户的UserRole数据
        if(userEx.getRoleId() != null){
            JSONObject object = new JSONObject();
            object.put("type", UserConstant.USER_BUSINESS_USER_ROLE);
            object.put("key_id", userEx.getId());
            object.put("value", "[" + userEx.getRoleId() + "]");
            Long aLong = userBusinessService.checkIsValueExist(UserConstant.USER_BUSINESS_USER_ROLE, userEx.getId());
            if(aLong != null){
                object.put("id", aLong);
                userBusinessService.updateObj(object, token);
            }else {
                userBusinessService.addObj(object, token);
            }
        }
        //如果用户没有组织直接跳过，返回
        if(userEx.getOrgaId() == null){
            return;
        }
        if("1".equals(userEx.getLeaderFlag())){
            int leaderCount = userMapper.getLeaderListByOrgaId(userEx.getOrgaId(), userEx.getId());
            if(leaderCount > 0){
                throw new BusinessException(ExceptionConstant.USER_LEADER_IS_EXIST_CODE, ExceptionConstant.USER_LEADER_IS_EXIST_MSG);
            }
        }
        OrgaUserRel orgaUserRel = new OrgaUserRel();
        orgaUserRel.setOrgaId(userEx.getOrgaId());
        orgaUserRel.setUserId(userEx.getId());
        orgaUserRel.setUserBlngOrgaDsplSeq(userEx.getUserBlngOrgaDsplSeq());
        int rows;
        if(orgaUserRel.getId() != null) {
            rows = orgaUserRelService.updateOrgeUserRel(orgaUserRel, token);
        }else{
            rows = orgaUserRelService.addOrgaUserRel(orgaUserRel, token);
        }
        if(rows < 1){
            throw new BusinessException(ExceptionConstant.ORGA_USER_REL_ADD_FAILED_CODE, ExceptionConstant.ORGA_USER_REL_ADD_FAILED_MSG);
        }
        logService.insertLog("用户", "新增用户" + userEx.getLoginName() + "成功", 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetPwdById(Long id) throws NoSuchAlgorithmException {
        User user = userMapper.selectById(id);
        if(user != null){
            logService.insertLog("用户", "重置" + id + "密码", 1);
            if("admin".equals(user.getLoginName())){
                log.info("禁止重置超管密码");
            }else{
                String password = CommonUtil.md5Encryp(BusinessConstant.USER_DEFAULT_PASSWORD);
                UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().eq("id", id).set("password", password);
                return userMapper.update(null, updateWrapper);
            }
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchSetStatus(String ids, Integer status, String token) {
        Integer userNumLimit = Integer.parseInt((String) redisService.queryMsgByKey(token, "userNumLimit"));
        List<Long> idList = StringUtil.strToLongList(ids);
        List<User> userList = userMapper.selectBatchIds(idList);
        int size = userList.size();
        long normalNum = countNormalUser();
        if(size + normalNum > userNumLimit && status == 0){
            throw new BusinessException(ExceptionConstant.USER_ENABLE_OVER_LIMIT_FAILED_CODE, ExceptionConstant.USER_ENABLE_OVER_LIMIT_FAILED_MSG);
        }
        if (status == 2) {
            for (User user:
                 userList) {
                //id和租户id相同说明是租户角色的用户，不能被禁用
                if(user.getId() == user.getTenantId()){
                    idList.remove(user.getId());
                }
            }
        }
        String updateStr = status == 0 ? "批量启用" : "批量禁用";
        if(idList.size() > 0){
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().in("id", idList).set("status", status);
            int result = userMapper.update(null, updateWrapper);
            logService.insertLog("用户", updateStr + "id为" + ids + "的用户", 1);
            return result;
        }
        return 0;
    }

    /**
     * 给用户设置一些默认值，然后插入数据库
     * @param userEx
     * @return
     */
    private UserEx addUserWithDefault(UserEx userEx) throws NoSuchAlgorithmException {
        userEx.setPassword(CommonUtil.md5Encryp(BusinessConstant.USER_DEFAULT_PASSWORD));
        userEx.setIsystem(BusinessConstant.USER_NOT_SYSTEM);
        if(userEx.getIsmanager() == null){
            userEx.setIsmanager(BusinessConstant.USER_NOT_MANAGER);
        }
        userEx.setStatus(BusinessConstant.USER_STATUS_NORMAL);
        int insert = userMapper.insert(userEx);
        if(insert > 0){
            return userEx;
        }
        return null;
    }

    @Override
    public List<? extends User> select(Map<String, String> paramMap) {
        String search = paramMap.get("search");
        String userName = StringUtil.getInfo(search, "userName");
        String loginName = StringUtil.getInfo(search, "loginName");
        Integer currentPage = StringUtil.parseInteger(paramMap.get("currentPage"));
        Integer pageSize = StringUtil.parseInteger(paramMap.get("pageSize"));
        List<UserEx> userList = userMapper.selectUserListByUserNameAndLoginName(userName,
                loginName, (currentPage - 1) * pageSize, pageSize);
        for (UserEx u:
             userList) {
            String userType = "普通";
            if(u.getId().equals(u.getTenantId())){
                userType = "租户";
            }else if(u.getTenantId() == null){
                userType = "管理员";
            }
            u.setUserType(userType);
            if("1".equals(u.getLeaderFlag())){
                u.setLeaderFlagStr("是");
            }else {
                u.setLeaderFlagStr("否");
            }
        }

        return userList;
    }

    @Override
    public Integer counts(Map<String, String> parameterMap) {
        String search = parameterMap.get("search");
        String userName = StringUtil.getInfo(search, "userName");
        String loginName = StringUtil.getInfo(search, "loginName");
        return userMapper.countsByUserNameAndLoginName(userName, loginName);
    }

    @Override
    public int delete(Long id, String token) {
        return deleteBatchIds(id.toString(), token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatchIds(String ids, String token) {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<User> users = userMapper.selectBatchIds(idList);
        for (User user:
             users) {
            if(user.getId() == user.getTenantId()){
                throw new BusinessException(ExceptionConstant.USER_LIMIT_TENANT_DELETE_CODE, ExceptionConstant.USER_LIMIT_TENANT_DELETE_MSG);
            }
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().in("id", idList).set("Status", 1);
        int batchDel = userMapper.update(null, updateWrapper);
        logService.insertLog("用户", "批量删除id为" + ids + "的用户", 1);
        return batchDel;
    }

    @Override
    public int deleteBatch(String ids, String token) {
        return deleteBatchIds(ids, token);
    }
}




