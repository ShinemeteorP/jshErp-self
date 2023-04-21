package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.controller.SystemConfigController;
import com.meteor.jsherp.domain.*;
import com.meteor.jsherp.mapper.DepotHeadMapper;
import com.meteor.jsherp.service.*;
import com.meteor.jsherp.service.common.ResourceInfo;
import com.meteor.jsherp.utils.CommonUtil;
import com.meteor.jsherp.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @author 刘鑫
 * @description 针对表【jsh_depot_head(单据主表)】的数据库操作Service实现
 * @createDate 2023-04-13 18:07:54
 */
@Service
@ResourceInfo("depotHead")
public class DepotHeadServiceImpl extends CommonServiceImpl<DepotHeadMapper, DepotHead>
        implements DepotHeadService {

    @Resource
    private DepotHeadMapper depotHeadMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private OrgaUserRelService orgaUserRelService;

    @Resource
    private UserService userService;

    @Resource
    private DepotService depotService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private PersonService personService;

    @Resource
    private AccountService accountService;

    @Resource
    private SystemConfigController systemConfigController;

    @Resource
    private AccountItemService accountItemService;

    @Resource
    private DepotItemService depotItemService;

    @Resource
    private MaterialService materialService;


    @Override
    public Map<String, Object> getBuyAndSaleStatistics(long[] userIds, long userId, String approval) {
        String priceLimit = roleService.getRoleByUserId(userId).getPriceLimit();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BusinessConstant.DEFAULT_DATETIME_FORMAT);
        HashMap<String, Object> staticsMap = new HashMap<>();
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());
        String todayBegin = formatter.format(now.atTime(0, 0, 0));
        String todayEnd = formatter.format(LocalDateTime.now());
        String yesterdayBegin = formatter.format(yesterday.atTime(0, 0, 0));
        String yesterdayEnd = formatter.format(yesterday.atTime(23, 59, 59));
        String monthBegin = formatter.format(firstDayOfMonth.atTime(0, 0, 0));
        String monthEnd = formatter.format(LocalDateTime.now());
        String yearBegin = formatter.format(firstDayOfYear.atTime(0, 0, 0));
        String yearEnd = formatter.format(LocalDateTime.now());
        int hasSupplier = 1;
        BigDecimal todayBuyInput = getBuyInput(hasSupplier, todayBegin, todayEnd, userIds, approval);
        BigDecimal todayBuyOutput = getBuyOutput(hasSupplier, todayBegin, todayEnd, userIds, approval);
        BigDecimal todaySaleInput = getSaleInput(hasSupplier, todayBegin, todayEnd, userIds, approval);
        BigDecimal todaySaleOutput = getSaleOutput(hasSupplier, todayBegin, todayEnd, userIds, approval);
        BigDecimal todayRetailSaleInput = getRetailSaleInput(hasSupplier, todayBegin, todayEnd, userIds, approval);
        BigDecimal todayRetailSaleOutput = getRetailSaleOutput(hasSupplier, todayBegin, todayEnd, userIds, approval);
        BigDecimal todayBuy = todayBuyInput.subtract(todayBuyOutput);
        BigDecimal todaySale = todaySaleOutput.subtract(todaySaleInput);
        BigDecimal todayRetailSale = todayRetailSaleOutput.subtract(todayRetailSaleInput);

        BigDecimal yesterdayBuyInput = getBuyInput(hasSupplier, yesterdayBegin, yesterdayEnd, userIds, approval);
        BigDecimal yesterdayBuyOutput = getBuyOutput(hasSupplier, yesterdayBegin, yesterdayEnd, userIds, approval);
        BigDecimal yesterdaySaleInput = getSaleInput(hasSupplier, yesterdayBegin, yesterdayEnd, userIds, approval);
        BigDecimal yesterdaySaleOutput = getSaleOutput(hasSupplier, yesterdayBegin, yesterdayEnd, userIds, approval);
        BigDecimal yesterdayRetailSaleInput = getRetailSaleInput(hasSupplier, yesterdayBegin, yesterdayEnd, userIds, approval);
        BigDecimal yesterdayRetailSaleOutput = getRetailSaleOutput(hasSupplier, yesterdayBegin, yesterdayEnd, userIds, approval);
        BigDecimal yesterdayBuy = yesterdayBuyInput.subtract(yesterdayBuyOutput);
        BigDecimal yesterdaySale = yesterdaySaleOutput.subtract(yesterdaySaleInput);
        BigDecimal yesterdayRetailSale = yesterdayRetailSaleOutput.subtract(yesterdayRetailSaleInput);

        BigDecimal monthBuyInput = getBuyInput(hasSupplier, monthBegin, monthEnd, userIds, approval);
        BigDecimal monthBuyOutput = getBuyOutput(hasSupplier, monthBegin, monthEnd, userIds, approval);
        BigDecimal monthSaleInput = getSaleInput(hasSupplier, monthBegin, monthEnd, userIds, approval);
        BigDecimal monthSaleOutput = getSaleOutput(hasSupplier, monthBegin, monthEnd, userIds, approval);
        BigDecimal monthRetailSaleInput = getRetailSaleInput(hasSupplier, monthBegin, monthEnd, userIds, approval);
        BigDecimal monthRetailSaleOutput = getRetailSaleOutput(hasSupplier, monthBegin, monthEnd, userIds, approval);
        BigDecimal monthBuy = monthBuyInput.subtract(monthBuyOutput);
        BigDecimal monthSale = monthSaleOutput.subtract(monthSaleInput);
        BigDecimal monthRetailSale = monthRetailSaleOutput.subtract(monthRetailSaleInput);

        BigDecimal yearBuyInput = getBuyInput(hasSupplier, yearBegin, yearEnd, userIds, approval);
        BigDecimal yearBuyOutput = getBuyOutput(hasSupplier, yearBegin, yearEnd, userIds, approval);
        BigDecimal yearSaleInput = getSaleInput(hasSupplier, yearBegin, yearEnd, userIds, approval);
        BigDecimal yearSaleOutput = getSaleOutput(hasSupplier, yearBegin, yearEnd, userIds, approval);
        BigDecimal yearRetailSaleInput = getRetailSaleInput(hasSupplier, yearBegin, yearEnd, userIds, approval);
        BigDecimal yearRetailSaleOutput = getRetailSaleOutput(hasSupplier, yearBegin, yearEnd, userIds, approval);
        BigDecimal yearBuy = yearBuyInput.subtract(yearBuyOutput);
        BigDecimal yearSale = yearSaleOutput.subtract(yearSaleInput);
        BigDecimal yearRetailSale = yearRetailSaleOutput.subtract(yearRetailSaleInput);

        staticsMap.put(BusinessConstant.TODAY_BUY,
                BusinessConstant.SHIELD_BUY_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : todayBuy);
        staticsMap.put(BusinessConstant.TODAY_SALE,
                BusinessConstant.SHIELD_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : todaySale);
        staticsMap.put(BusinessConstant.TODAY_RETAIL_SALE,
                BusinessConstant.SHIELD_RETAIL_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : todayRetailSale);
        staticsMap.put(BusinessConstant.YESTERDAY_BUY,
                BusinessConstant.SHIELD_BUY_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : yesterdayBuy);
        staticsMap.put(BusinessConstant.YESTERDAY_SALE,
                BusinessConstant.SHIELD_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : yesterdaySale);
        staticsMap.put(BusinessConstant.YESTERDAY_RETAIL_SALE,
                BusinessConstant.SHIELD_RETAIL_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : yesterdayRetailSale);
        staticsMap.put(BusinessConstant.MONTH_BUY,
                BusinessConstant.SHIELD_BUY_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : monthBuy);
        staticsMap.put(BusinessConstant.MONTH_SALE,
                BusinessConstant.SHIELD_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : monthSale);
        staticsMap.put(BusinessConstant.MONTH_RETAIL_SALE,
                BusinessConstant.SHIELD_RETAIL_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : monthRetailSale);
        staticsMap.put(BusinessConstant.YEAR_BUY,
                BusinessConstant.SHIELD_BUY_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : yearBuy);
        staticsMap.put(BusinessConstant.YEAR_SALE,
                BusinessConstant.SHIELD_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : yearSale);
        staticsMap.put(BusinessConstant.YEAR_RETAIL_SALE,
                BusinessConstant.SHIELD_RETAIL_SALE_PRICE.equals(priceLimit) ? BusinessConstant.PRICE_SHIELD_MARK : yearRetailSale);

        return staticsMap;
    }

    @Override
    public BigDecimal getBuyInput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval) {
        String type = "入库";
        String subType = "采购";
        BigDecimal decimal = depotHeadMapper.getAnySaleStatistics(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        return decimal == null ? new BigDecimal(0) : decimal;
    }

    @Override
    public BigDecimal getBuyOutput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval) {
        String type = "出库";
        String subType = "采购退货";
        BigDecimal decimal = depotHeadMapper.getAnySaleStatistics(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        return decimal == null ? new BigDecimal(0) : decimal;
    }

    @Override
    public BigDecimal getSaleInput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval) {
        String type = "入库";
        String subType = "销售退货";
        BigDecimal decimal = depotHeadMapper.getAnySaleStatistics(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        return decimal == null ? new BigDecimal(0) : decimal;
    }

    @Override
    public BigDecimal getSaleOutput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval) {
        String type = "出库";
        String subType = "销售";
        BigDecimal decimal = depotHeadMapper.getAnySaleStatistics(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        return decimal == null ? new BigDecimal(0) : decimal;
    }

    @Override
    public BigDecimal getRetailSaleInput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval) {
        String type = "入库";
        String subType = "零售退货";
        BigDecimal decimal = depotHeadMapper.getRetailSaleStatistics(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        return decimal == null ? new BigDecimal(0) : decimal;
    }

    @Override
    public BigDecimal getRetailSaleOutput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval) {
        String type = "出库";
        String subType = "零售";

        BigDecimal decimal = depotHeadMapper.getRetailSaleStatistics(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        return decimal == null ? new BigDecimal(0) : decimal;
    }

    @Override
    public List<DepotHeadBo> select(Map<String, String> paramMap) {
        JSONObject search = (JSONObject) JSON.parse(paramMap.get("search"));
        String type = (String) search.get("type");
        String subType = (String) search.get("subType");
        String roleType = (String) search.get("roleType");
        String status = (String) search.get("status");
        String hasDebt = (String) search.get("hasDebt");
        String purchaseStatus = (String) search.get("purchaseStatus");
        String number = (String) search.get("number");
        String linkNumber = (String) search.get("linkNumber");
        String beginTime = (String) search.get("beginTime");
        String endTime = (String) search.get("endTime");
        String materialParam = (String) search.get("materialParam");
        String remark = (String) search.get("remark");
        Long organId = StringUtil.parseStrLong((String) search.get("organId"));
        Long creator = StringUtil.parseStrLong((String) search.get("creator"));
        Long depotId = StringUtil.parseStrLong((String) search.get("depotId"));
        Long accountId = StringUtil.parseStrLong((String) search.get("accountId"));
        Integer currentPage = StringUtil.parseInteger(paramMap.get(ErpAllConstant.REQUEST_PARAM_CURRENT_PAGE));
        Integer pageSize = StringUtil.parseInteger(paramMap.get(ErpAllConstant.REQUEST_PARAM_PAGE_SIZE));
        User user = userService.getLoginUser(paramMap.get("token"));
        long[] userIdList = orgaUserRelService.getUserIdListByRole(user.getId(), (String) roleType);

        String[] statusArray = StringUtils.hasText(status) ? status.split(",") : null;
        String[] purchaseStatusArray = StringUtils.hasText(status) ? purchaseStatus.split(",") : null;

        List<Long> depotArray = null;
        String depotFlag = systemConfigController.getCurrent().getDepotFlag();
        if (!"销售订单".equals(subType) && !"采购订单".equals(subType)) {
            depotArray = depotService.getDepotIds(user.getId(), depotFlag);
        }
        List<Organization> organArray = organizationService.getOrangArray(user.getId(), subType, purchaseStatus);
        Map<Long, String> personMap = personService.getPersonIdAndName();
        Map<Long, String> accountMap = accountService.getAccountIdAndName();
        List<DepotHeadBo> depotHeadList = getDepotHeadBoList(type, subType, userIdList, hasDebt, statusArray, purchaseStatusArray, number,
                linkNumber, beginTime, endTime, materialParam, organId, organArray, creator, depotId, depotArray,
                accountId, remark, (currentPage - 1) * pageSize, pageSize, personMap, accountMap);
        return depotHeadList;
    }

    private List<DepotHeadBo> getDepotHeadBoList(String type, String subType, long[] userIdList,
                                                 String hasDebt, String[] statusArray, String[] purchaseStatusArray,
                                                 String number, String linkNumber, String beginTime, String endTime,
                                                 String materialParam, Long organId, List<Organization> organArray,
                                                 Long creator, Long depotId, List<Long> depotArray, Long accountId,
                                                 String remark, int start, Integer pageSize, Map personMap, Map accountMap) {
        List<DepotHeadBo> depotHeadBoList = depotHeadMapper.getDepotHeadBoList(type, subType, userIdList, hasDebt, statusArray, purchaseStatusArray, number,
                linkNumber, beginTime, endTime, materialParam, organId, organArray, creator, depotId, depotArray,
                accountId, remark, start, pageSize);
        if (depotHeadBoList != null) {
            ArrayList<Long> idList = new ArrayList<>();
            ArrayList<String> numberList = new ArrayList<>();
            for (DepotHeadBo d :
                    depotHeadBoList) {
                idList.add(d.getId());
                numberList.add(d.getNumber());
            }
            Map<Long, Long> financialBillNoMap = accountItemService.getBillCountMap(idList);
            Map<Long, String> materialListMap = materialService.getMaterialListMap(idList);
            Map<String, Long> billSizeMap = getBillSizeMap(numberList);
            Map<String, BigDecimal> finishDepositMap = getFinishDepositMap(numberList);
            Map<Long, BigDecimal> materialCountListMap = depotItemService.getMaterialCountListMap(idList);
            for (DepotHeadBo d :
                    depotHeadBoList) {
                if (accountMap != null && StringUtils.hasText(d.getAccountIdList())
                        && StringUtils.hasText(d.getAccountMoneyList())) {
                    List<BigDecimal> bigDecimals = StringUtil.strToBigDecimalList(d.getAccountMoneyList());
                    List<Long> longs = StringUtil.strToLongList(d.getAccountIdList());
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < longs.size(); i++) {
                        stringBuilder.append(accountMap.get(longs.get(i)) + "(" + bigDecimals.get(i).abs() + "元)");
                    }
                    d.setAccountName(stringBuilder.toString());
                }
                if (d.getAccountIdList() != null) {
                    String accountIdList = d.getAccountIdList();
                    d.setAccountIdList(accountIdList.replaceAll("\\{", "")
                            .replaceAll("}", "").replaceAll("\\[", "")
                            .replaceAll("]", ""));
                }
                if (d.getAccountMoneyList() != null) {
                    String accountMoneyList = d.getAccountMoneyList();
                    d.setAccountMoneyList(accountMoneyList.replaceAll("\\{", "")
                            .replaceAll("}", "").replaceAll("\\[", "")
                            .replaceAll("]", ""));
                }
                if (d.getChangeAmount() != null) {
                    d.setChangeAmount(d.getChangeAmount().abs());
                } else {
                    d.setChangeAmount(BigDecimal.valueOf(0));
                }
                if (d.getTotalPrice() != null) {
                    d.setTotalPrice(d.getTotalPrice().abs());
                }
                if (d.getDeposit() == null) {
                    d.setDeposit(BigDecimal.valueOf(0));
                }
                if (finishDepositMap != null && finishDepositMap.size() > 0) {
                    d.setFinishDeposit(
                            finishDepositMap.get(d.getNumber()) != null
                                    ? finishDepositMap.get(d.getNumber())
                                    : BigDecimal.valueOf(0));
                }
                BigDecimal discountLastMoney = d.getDiscountLastMoney() != null ? d.getDiscountLastMoney() : BigDecimal.valueOf(0);
                BigDecimal otherMoney = d.getOtherMoney() != null ? d.getOtherMoney() : BigDecimal.valueOf(0);
                BigDecimal deposit = d.getDeposit() != null ? d.getDeposit() : BigDecimal.valueOf(0);
                BigDecimal changeAmount = d.getChangeAmount() != null ? d.getChangeAmount() : BigDecimal.valueOf(0);

                d.setDebt(discountLastMoney.add(otherMoney).subtract(deposit).subtract(changeAmount));
                if (financialBillNoMap != null && financialBillNoMap.size() > 0) {
                    d.setHasFinancialFlag(financialBillNoMap.get(d.getId()) != null
                            && financialBillNoMap.get(d.getId()) > 0);
                }
                if (billSizeMap != null && billSizeMap.size() > 0) {
                    d.setHasBackFlag(billSizeMap.get(d.getNumber()) != null
                            && billSizeMap.get(d.getNumber()) > 0);
                }
                if (d.getSalesMan() != null){
                    List<Long> list = StringUtil.strToLongList(d.getSalesMan());
                    StringBuilder builder = new StringBuilder();
                    for (Long l:
                         list) {
                        builder.append(personMap.get(l) + " ");
                    }
                    d.setSalesManStr(builder.toString());
                }
                d.setOperTimeStr(CommonUtil.parseDateToStr(d.getOperTime()));
                if (materialListMap != null && materialListMap.size() > 0){
                    d.setMaterialsList(materialListMap.get(d.getId()));
                }
                if (materialCountListMap != null && materialCountListMap.size() > 0){
                    d.setMaterialCount(materialCountListMap.get(d.getId()));
                }
                if (d.getPurchaseStatus() != null){
                    d.setOrganName("***");
                    d.setTotalPrice(BigDecimal.valueOf(0));
                    d.setDiscountLastMoney(BigDecimal.valueOf(0));
                }

            }
        }


        return depotHeadBoList;
    }

    private Map<String, BigDecimal> getFinishDepositMap(ArrayList<String> numberList) {
        QueryWrapper<DepotHead> in = new QueryWrapper<DepotHead>().in("link_number", numberList);
        HashMap<String, BigDecimal> map = new HashMap<>();
        List<DepotHead> depotItems = depotHeadMapper.selectList(in);
        BigDecimal totalDeposit = new BigDecimal(0);
        for (DepotHead d :
                depotItems) {
            totalDeposit = totalDeposit.add(d.getDeposit() != null ? d.getDeposit() : BigDecimal.valueOf(0));
        }
        for (DepotHead d :
                depotItems) {
            map.put(d.getLinkNumber(), totalDeposit);
        }
        return map;
    }

    @Override
    public Map<String, Long> getBillSizeMap(List<String> numberList) {
        QueryWrapper<DepotHead> in = new QueryWrapper<DepotHead>().in("link_number", numberList);
        HashMap<String, Long> map = new HashMap<>();
        List<DepotHead> depotItems = depotHeadMapper.selectList(in);
        for (DepotHead d :
                depotItems) {
            map.put(d.getLinkNumber(), Long.valueOf(depotItems.size()));
        }
        return map;
    }
}




