package com.meteor.jsherp.service.impl;

import com.meteor.jsherp.JshErpSelfApplication;
import com.meteor.jsherp.controller.UserController;
import com.meteor.jsherp.domain.Tenant;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.extand.UserEx;
import com.meteor.jsherp.service.TenantService;
import com.meteor.jsherp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@SpringBootTest(classes = JshErpSelfApplication.class)
@RunWith(SpringRunner.class)
public class TenantServiceImplTest {

    @Resource
    private TenantService tenantService;

    @Resource
    private UserService userService;

    @Resource
    private UserController userController;

    @Test
    public void register(){
        UserEx userEx = new UserEx();
        userEx.setLoginName("lucy");
        userEx.setPassword("123456");
        userController.registerUser(userEx);
    }

    @Test
    public void test(){
        List<Tenant> tenants = tenantService.getListByKey("tenant_id", 63);
        System.out.println( tenants.get(0));
        List<User> users = userService.getListByKey("login_name", "meteor_zm");
        System.out.println( users.get(0));
        User byId = userService.getById(63);
        System.out.println( byId);
    }

}