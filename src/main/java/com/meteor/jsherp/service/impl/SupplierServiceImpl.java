package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.controller.SystemConfigController;
import com.meteor.jsherp.domain.Supplier;
import com.meteor.jsherp.domain.UserBusiness;
import com.meteor.jsherp.mapper.SupplierMapper;
import com.meteor.jsherp.service.SupplierService;
import com.meteor.jsherp.service.UserBusinessService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.service.common.ResourceInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_supplier(供应商/客户信息表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@ResourceInfo("supplier")
@Service
public class SupplierServiceImpl extends CommonServiceImpl<SupplierMapper, Supplier>
    implements SupplierService{

    @Resource
    private SupplierMapper supplierMapper;

    @Resource
    private UserService userService;

    @Resource
    private SystemConfigController systemConfigController;

    @Resource
    private UserBusinessService userBusinessService;

    @Override
    public List<Supplier> findBySelect(String type) {
        QueryWrapper<Supplier> wrapper = new QueryWrapper<Supplier>().like("type", type).eq("enabled", true)
                .orderByAsc("sort").orderByDesc("id");
        List<Supplier> suppliers = supplierMapper.selectList(wrapper);
        return suppliers;
    }

    @Override
    public int updateAdvanceInById(Long id, BigDecimal advanceIn) {

        return supplierMapper.updateAdvanceInById(id, advanceIn);
    }

    @Override
    public JSONArray findBySelectMember() {
        JSONArray array = new JSONArray();
        List<Supplier> dataList = findBySelect("会员");
        for (Supplier supplier:
                dataList) {
            JSONObject object = new JSONObject();
            object.put("id", supplier.getId());
            object.put("supplier", supplier.getSupplier());
            object.put("advanceIn", supplier.getAdvanceIn());
            array.add(object);
        }
        return array;
    }

    @Override
    public JSONArray findBySelectSup() {
        JSONArray array = new JSONArray();
        List<Supplier> suppliers = findBySelect("供应商");
        for(Supplier supplier : suppliers){
            JSONObject object = new JSONObject();
            object.put("id", supplier.getId());
            object.put("supplier", supplier.getSupplier());
            array.add(object);
        }
        return array;
    }

    @Override
    public JSONArray findBySelectCus(String token) {
        JSONArray array = new JSONArray();
        Long userId = userService.getLoginUser(token).getId();
        List<UserBusiness> userBusinessList = userBusinessService.getListByKeyMap(
                ImmutableMap.of("key_id", userId, "type", UserConstant.USER_BUSINESS_USER_CUSTOMER));
        List<Supplier> suppliers = findBySelect("客户");
        if(userBusinessList != null){
            String ubValue = userBusinessList.get(0).getValue();
            String customerFlag = systemConfigController.getCurrent().getCustomerFlag();
            for (Supplier s:
                 suppliers) {
                boolean contains = ubValue.contains("[" + s.getId() + "]");
                if("0".equals(customerFlag) || contains){
                    JSONObject object = new JSONObject();
                    object.put("id", s.getId());
                    object.put("supplier", s.getSupplier());
                    array.add(object);
                }
            }
        }
        return array;
    }

    @Override
    public JSONArray findBySelectOrgan(String token) {
        JSONArray array = new JSONArray();
        JSONArray supArray = findBySelectSup();
        for (int i = 0; i < supArray.size(); i++) {
            JSONObject object = (JSONObject) supArray.get(i);
            object.put("supplier", object.getString("supplier") + "[供应商]");
            array.add(object);
        }
        JSONArray cusArray = findBySelectCus(token);
        for (int i = 0; i < cusArray.size(); i++) {
            JSONObject obj = (JSONObject) cusArray.get(i);
            obj.put("supplier", obj.getString("supplier") + "[客户]");
            array.add(obj);
        }
        return array;
    }


}




