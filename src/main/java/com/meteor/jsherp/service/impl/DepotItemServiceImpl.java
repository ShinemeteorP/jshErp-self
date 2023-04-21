package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.domain.DepotItem;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.mapper.DepotItemMapper;
import com.meteor.jsherp.service.*;
import com.meteor.jsherp.utils.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_depot_item(单据子表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class DepotItemServiceImpl extends CommonServiceImpl<DepotItemMapper, DepotItem>
    implements DepotItemService {

    @Resource
    private DepotHeadService depotHeadService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private OrgaUserRelService orgaUserRelService;

    @Resource
    private DepotItemMapper depotItemMapper;

    @Override
    public Map<String, Object> getMonthsStatics(String token, String type, String approvalFlag) throws Exception{
        User user = userService.getLoginUser(token);
        HashMap<String, Object> map = new HashMap<>();
        String priceLimit = roleService.getRoleByUserId(user.getId()).getPriceLimit();
        long[] userIdList = orgaUserRelService.getUserIdListByRole(user.getId(), type);
        List<String> lastMonths = CommonUtil.getLastMonths(6);
        JSONArray buyPriceList = new JSONArray();
        JSONArray salePriceList = new JSONArray();
        JSONArray retailPriceList = new JSONArray();
        for (String month:
             lastMonths) {
            String beginTime = CommonUtil.firstDayOfMonth(month) + BusinessConstant.DAY_FIRST_TIME;
            String endTime = CommonUtil.lastDayOfMonth(month) + BusinessConstant.DAY_LAST_TIME;
            BigDecimal buyInput = depotHeadService.getBuyInput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal buyOutput = depotHeadService.getBuyOutput(0, beginTime, endTime, userIdList, approvalFlag);
            JSONObject buyObject = new JSONObject();
            BigDecimal buy = buyInput.subtract(buyOutput);
            buyObject.put("x", month);
            buyObject.put("y",
                    BusinessConstant.SHIELD_BUY_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : buy);
            buyPriceList.add(buyObject);

            BigDecimal saleInput = depotHeadService.getSaleInput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal saleOutput = depotHeadService.getSaleOutput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal sale = saleOutput.subtract(saleInput);
            JSONObject saleObject = new JSONObject();
            saleObject.put("x", month);
            saleObject.put("y",
                    BusinessConstant.SHIELD_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : sale);
            salePriceList.add(saleObject);


            BigDecimal retailSaleInput = depotHeadService.getRetailSaleInput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal retailSaleOutput = depotHeadService.getRetailSaleOutput(0, beginTime, endTime, userIdList, approvalFlag);
            BigDecimal retail = retailSaleOutput.subtract(retailSaleInput);
            JSONObject retailObject = new JSONObject();
            retailObject.put("x", month);
            retailObject.put("y",
                    BusinessConstant.SHIELD_RETAIL_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : retail);
            retailPriceList.add(retailObject);
        }
        map.put("buyPriceList", buyPriceList);
        map.put("salePriceList", salePriceList);
        map.put("retailPriceList", retailPriceList);

        return map;
    }

    @Override
    public Map<Long, BigDecimal> getMaterialCountListMap(List<Long> idList) {
        QueryWrapper<DepotItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("header_id", idList);
        List<DepotItem> depotItems = depotItemMapper.selectList(queryWrapper);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (DepotItem d:
             depotItems) {
            sum = sum.add(d.getOperNumber() != null ? d.getOperNumber() : BigDecimal.valueOf(0));
        }
        HashMap<Long, BigDecimal> hashMap = new HashMap<>();
        for (DepotItem d:
            depotItems ) {
            hashMap.put(d.getHeaderId(), sum);
        }
        return hashMap;
    }


}




