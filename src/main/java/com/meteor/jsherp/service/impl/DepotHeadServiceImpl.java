package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.ImmutableMap;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.controller.SystemConfigController;
import com.meteor.jsherp.domain.*;
import com.meteor.jsherp.domain.extand.DepotHeadExReBody;
import com.meteor.jsherp.domain.extand.DepotHeadEx;
import com.meteor.jsherp.mapper.DepotHeadMapper;
import com.meteor.jsherp.service.*;
import com.meteor.jsherp.service.common.ResourceInfo;
import com.meteor.jsherp.utils.CommonUtil;
import com.meteor.jsherp.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Resource
    private SupplierService supplierService;

    @Resource
    private SerialNumberService serialNumberService;


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
        List<DepotHead> test = depotHeadMapper.test(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        BigDecimal sum = BigDecimal.ZERO;
        for (DepotHead dh:
                test) {
            sum = sum.add(dh.getTotalPrice());
        }
        BigDecimal decimal = depotHeadMapper.getRetailSaleStatistics(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        return decimal == null ? new BigDecimal(0) : decimal;
    }

    @Override
    public BigDecimal getRetailSaleOutput(int hasSupplier, String startTime, String endTime, long[] userIds, String approval) {
        String type = "出库";
        String subType = "零售";
        List<DepotHead> test = depotHeadMapper.test(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        BigDecimal sum = BigDecimal.ZERO;
        for (DepotHead dh:
             test) {
            sum = sum.add(dh.getTotalPrice());
        }
        BigDecimal decimal = depotHeadMapper.getRetailSaleStatistics(type, subType, hasSupplier, startTime, endTime, userIds, approval);
        return decimal == null ? new BigDecimal(0) : decimal;
    }

    @Override
    public List<DepotHeadEx> select(Map<String, String> paramMap) {
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
        List<DepotHeadEx> depotHeadList = getDepotHeadBoList(type, subType, userIdList, hasDebt, statusArray, purchaseStatusArray, number,
                linkNumber, beginTime, endTime, materialParam, organId, organArray, creator, depotId, depotArray,
                accountId, remark, (currentPage - 1) * pageSize, pageSize, personMap, accountMap);
        return depotHeadList;
    }

    private List<DepotHeadEx> getDepotHeadBoList(String type, String subType, long[] userIdList,
                                                 String hasDebt, String[] statusArray, String[] purchaseStatusArray,
                                                 String number, String linkNumber, String beginTime, String endTime,
                                                 String materialParam, Long organId, List<Organization> organArray,
                                                 Long creator, Long depotId, List<Long> depotArray, Long accountId,
                                                 String remark, int start, Integer pageSize, Map personMap, Map accountMap) {
        List<DepotHeadEx> depotHeadExList = depotHeadMapper.getDepotHeadBoList(type, subType, userIdList, hasDebt, statusArray, purchaseStatusArray, number,
                linkNumber, beginTime, endTime, materialParam, organId, organArray, creator, depotId, depotArray,
                accountId, remark, start, pageSize);
        if (depotHeadExList != null) {
            ArrayList<Long> idList = new ArrayList<>();
            ArrayList<String> numberList = new ArrayList<>();
            for (DepotHeadEx d :
                    depotHeadExList) {
                idList.add(d.getId());
                numberList.add(d.getNumber());
            }
            Map<Long, Long> financialBillNoMap = accountItemService.getBillCountMap(idList);
            Map<Long, String> materialListMap = materialService.getMaterialListMapByHeaderId(idList);
            Map<String, Long> billSizeMap = getBillSizeMapByLinkNumber(numberList);
            Map<String, BigDecimal> finishDepositMap = getFinishDepositMap(numberList);
            Map<Long, BigDecimal> materialCountListMap = depotItemService.getMaterialCountListMap(idList);
            for (DepotHeadEx d :
                    depotHeadExList) {
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
                if (purchaseStatusArray != null){
                    d.setOrganName("***");
                    d.setTotalPrice(BigDecimal.valueOf(0));
                    d.setDiscountLastMoney(BigDecimal.valueOf(0));
                }

            }
        }


        return depotHeadExList;
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
    public Map<String, Long> getBillSizeMapByLinkNumber(List<String> numberList) {
        QueryWrapper<DepotHead> in = new QueryWrapper<DepotHead>().in("link_number", numberList);
        HashMap<String, Long> map = new HashMap<>();
        List<DepotHead> depotItems = depotHeadMapper.selectList(in);
        if(depotItems == null || depotItems.isEmpty()){
            return null;
        }
        for (DepotHead d :
                depotItems) {
            map.put(d.getLinkNumber(), Long.valueOf(depotItems.size()));
        }
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addDepotHeadAndDetail(DepotHeadExReBody depotHeadArg, String token) {
        String info = depotHeadArg.getInfo();
        String rows = depotHeadArg.getRows();
        DepotHead depotHead = JSONObject.parseObject(info, DepotHead.class);
        Integer count = depotHeadMapper.selectCount(new QueryWrapper<DepotHead>().eq("number", depotHead.getNumber()));
        if(count > 0){
            throw new RuntimeException("number已存在，数据添加失败");
        }
        String subType = depotHead.getSubType();
        //结算账户校验
        if("采购".equals(subType) || "采购退货".equals(subType) || "销售".equals(subType)|| "销售退货".equals(subType)){
            if(StringUtils.hasText(depotHead.getAccountIdList()) && depotHead.getAccountId() != null){
                throw new RuntimeException("结算账户不能为空");
            }
        }
        if("销售退货".equals(subType) || "采购退货".equals(subType)){
            //进行欠款检验
            checkDebtByParam(info, depotHead);
        }
        User user = userService.getLoginUser(token);
        depotHead.setCreator(user.getId());
        depotHead.setCreateTime(new Date());
        if(!StringUtils.hasText(depotHead.getStatus())) {
            depotHead.setStatus(BusinessConstant.BILLS_STATUS_AUDIT);
        }
        depotHead.setPurchaseStatus(BusinessConstant.BILLS_STATUS_UN_AUDIT);
        depotHead.setPayType(depotHead.getPayType()==null?"现付":depotHead.getPayType());
        if(StringUtils.hasText(depotHead.getAccountIdList())){
            depotHead.setAccountIdList(depotHead.getAccountIdList().replace("[","").replace("]","").replaceAll("\"",""));
        }
        if(StringUtils.hasText(depotHead.getAccountMoneyList())){
            //校验多账户的结算金额
            String accountMoneyStr = depotHead.getAccountMoneyList().replace("[", "").replace("]", "").replaceAll("\"", "");
            String[] split = accountMoneyStr.split(",");
            BigDecimal accountMoneySum = BigDecimal.ZERO;
            for (String s:
                 split) {
                accountMoneySum.add(new BigDecimal(s));
            }
            accountMoneySum = accountMoneySum.abs();
            if(!accountMoneySum.equals(depotHead.getChangeAmount().abs())){
                throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_MANY_ACCOUNT_FAILED_MSG);
            }
            depotHead.setAccountMoneyList(accountMoneyStr);
        }
        //校验累计扣除订金是否超出订单中的金额
        if(depotHead.getDeposit() != null && StringUtils.hasText(depotHead.getLinkNumber())){
            BigDecimal deposit = depotHeadMapper.getDepositByLinkNumberAndNumber(depotHead.getLinkNumber(), depotHead.getNumber());
            BigDecimal changeAmount = depotHeadMapper.selectOne(new QueryWrapper<DepotHead>().eq("number", depotHead.getLinkNumber())).getChangeAmount();
            if (changeAmount != null) {
                changeAmount = changeAmount.abs();
                if(depotHead.getDeposit().add(deposit).compareTo(changeAmount) > 0){
                    throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_DEPOSIT_OVER_PRE_MSG);
                }
            }
        }
        depotHeadMapper.insert(depotHead);
        //入库和出库处理预付款信息
        if("预付款".equals(depotHead.getPayType()) && depotHead.getOrganId() != null){
            Supplier supplier = supplierService.getById(depotHead.getOrganId());
            BigDecimal supplierAdvanceIn = supplier.getAdvanceIn();
            if(supplierAdvanceIn.compareTo(depotHead.getTotalPrice()) >= 0){
                supplier.setAdvanceIn(supplierAdvanceIn.subtract(depotHead.getTotalPrice()));
                supplierService.updateById(supplier);
            }else{
                throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_MEMBER_PAY_LACK_MSG);
            }
        }
        //  入库和出库处理单据子表信息，新增depotItem数据：
        depotItemService.saveDetail(rows,depotHead,"add", token);
        //日志数据补充
    }

    /**
     * 针对退货单进行欠款检验
     * @param info
     * @param depotHead
     */
    @Override
    public void checkDebtByParam(String info, DepotHead depotHead) {
        JSONObject jsonObject = JSONObject.parseObject(info);
        if(jsonObject != null) {
            BigDecimal debt = jsonObject.getBigDecimal("debt");
            if(debt != null){
                if(StringUtils.hasText(depotHead.getLinkNumber())){
                    BigDecimal originalDebt = getOriginalRealDebt(depotHead.getLinkNumber(), depotHead.getNumber());
                    if(originalDebt.compareTo(debt) < 0){
                        throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_BACK_BILL_DEBT_OVER_MSG);
                    }
                }else{
                    if(!debt.equals(BigDecimal.ZERO)){
                        throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_BACK_BILL_DEBT_FAILED_MSG);
                    }
                }
            }
        }
    }

    /**
     * 退货单对应的原单实际欠款（这里面要除去收付款的金额）
     * @param linkNumber 原单单号
     * @param number 当前单号
     * @return
     */
    @Override
    public BigDecimal getOriginalRealDebt(String linkNumber, String number) {
        DepotHead depotHead = depotHeadMapper.selectOne(new QueryWrapper<DepotHead>().eq("number", linkNumber));
        BigDecimal discountLastMoney = depotHead.getDiscountLastMoney()!=null?depotHead.getDiscountLastMoney():BigDecimal.ZERO;
        BigDecimal otherMoney = depotHead.getOtherMoney()!=null?depotHead.getOtherMoney():BigDecimal.ZERO;
        BigDecimal deposit = depotHead.getDeposit()!=null?depotHead.getDeposit():BigDecimal.ZERO;
        BigDecimal changeAmount = depotHead.getChangeAmount()!=null?depotHead.getChangeAmount().abs():BigDecimal.ZERO;
        //原单欠款
        BigDecimal debt = discountLastMoney.add(otherMoney).subtract((deposit.add(changeAmount)));
        //完成欠款
        BigDecimal completedDebt = accountItemService.getCompletedDebt(depotHead.getId());
        //原单对应退货单欠款
        List<DepotHead> depotHeadList = depotHeadMapper.selectList(new QueryWrapper<DepotHead>().eq("number", number).eq("link_number", linkNumber));
        BigDecimal debtSum = BigDecimal.ZERO;
        for (DepotHead d:
             depotHeadList) {
            BigDecimal thisDiscountLastMoney = depotHead.getDiscountLastMoney()!=null?depotHead.getDiscountLastMoney():BigDecimal.ZERO;
            BigDecimal thisOtherMoney = depotHead.getOtherMoney()!=null?depotHead.getOtherMoney():BigDecimal.ZERO;
            BigDecimal thisDeposit = depotHead.getDeposit()!=null?depotHead.getDeposit():BigDecimal.ZERO;
            BigDecimal thisChangeAmount = depotHead.getChangeAmount()!=null?depotHead.getChangeAmount().abs():BigDecimal.ZERO;
            //原单欠款this
            BigDecimal thisDebt = thisDiscountLastMoney.add(thisOtherMoney).subtract((thisDeposit.add(thisChangeAmount)));
            debtSum = debtSum.add(thisDebt);
        }
        return debt.subtract(completedDebt).subtract(debtSum);

    }

    @Override
    public List<DepotHeadEx> getDepotHeadExListByNumber(String number, String token) {
        Map<Long, String> personMap = personService.getPersonIdAndName();
        Map<Long, String> accountMap = accountService.getAccountIdAndName();
        List<DepotHeadEx> depotHeadExList = depotHeadMapper.getDepotHeadDetailByNumber(number);
        if(depotHeadExList != null){
            ArrayList<Long> idList = new ArrayList<>();
            ArrayList<String> numberList = new ArrayList<>();
            for (DepotHeadEx depotHeadEx:
                 depotHeadExList) {
                idList.add(depotHeadEx.getId());
                numberList.add(depotHeadEx.getNumber());
            }
            Map<Long, Long> billCountMap = accountItemService.getBillCountMap(idList);
            Map<String, Long> billSizeMap = getBillSizeMapByLinkNumber(numberList);
            Map<Long, String> materialMap = materialService.getMaterialListMapByHeaderId(idList);
            for (DepotHeadEx dh:
                    depotHeadExList) {
                if(accountMap != null && StringUtils.hasText(dh.getAccountIdList())
                        && StringUtils.hasText(dh.getAccountMoneyList())){
                    String accountStr = accountService.getAccountStrByIdAndMoney(accountMap,
                            dh.getAccountIdList(), dh.getAccountMoneyList());
                    dh.setAccountName(accountStr);
                }
                if(dh.getAccountIdList() != null){
                    String s = dh.getAccountIdList().replace("[", "")
                            .replace("]", "").replaceAll("\"", "");
                    dh.setAccountIdList(s);
                }
                if(dh.getAccountMoneyList() != null){
                    String s = dh.getAccountMoneyList().replace("[", "")
                            .replace("]", "").replaceAll("\"", "");
                    dh.setAccountMoneyList(s);
                }
                if(dh.getChangeAmount() != null) {
                    dh.setChangeAmount(dh.getChangeAmount().abs());
                }
                if(dh.getTotalPrice() != null) {
                    dh.setTotalPrice(dh.getTotalPrice().abs());
                }
                //是否有付款单或收款单
                if (billCountMap != null){
                    Long size = billCountMap.get(dh.getId());
                    dh.setHasFinancialFlag(size != 0 && size > 0);
                }
                if(billSizeMap != null){
                    Long size = billSizeMap.get(dh.getNumber());
                    dh.setHasBackFlag(size != 0 && size > 0);
                }
                if(StringUtils.hasText(dh.getSalesMan())){
                    dh.setSalesManStr(personService.getPersonByMapAndIds(personMap, dh.getSalesMan()));
                }
                dh.setOperTimeStr(CommonUtil.parseFullDateToStr(dh.getOperTime()));
                //商品信息简述
                if(materialMap != null){
                    dh.setMaterialsList(materialMap.get(dh.getId()));
                }
                dh.setCreatorName(userService.getLoginUser(token).getUsername());
            }
        }
        return depotHeadExList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDepotHeadAndDetail(DepotHeadExReBody depotHeadBody, String token) {
        String info = depotHeadBody.getInfo();
        String rows = depotHeadBody.getRows();
        DepotHead depotHead = JSONObject.parseObject(info, DepotHead.class);
        Integer count = depotHeadMapper.selectCount(new QueryWrapper<DepotHead>().ne("id", depotHead.getId()).eq("number", depotHead.getNumber()));
        if(count > 0){
            throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_BILL_NUMBER_EXIST_MSG);
        }
        DepotHead originalDh = depotHeadMapper.selectById(depotHead.getId());
        if(!"0".equals(originalDh.getStatus())){
            throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_BILL_CANNOT_EDIT_MSG);
        }

        String subType = depotHead.getSubType();
        //结算账户校验
        if("采购".equals(subType) || "采购退货".equals(subType) || "销售".equals(subType)|| "销售退货".equals(subType)){
            if(StringUtils.hasText(depotHead.getAccountIdList()) && depotHead.getAccountId() != null){
                throw new RuntimeException("结算账户不能为空");
            }
        }
        if("销售退货".equals(subType) || "采购退货".equals(subType)){
            //进行欠款检验
            checkDebtByParam(info, depotHead);
        }
        if(StringUtils.hasText(depotHead.getAccountIdList())){
            depotHead.setAccountIdList(depotHead.getAccountIdList().replace("[","").replace("]","").replaceAll("\"",""));
        }
        if(StringUtils.hasText(depotHead.getAccountMoneyList())){
            //校验多账户的结算金额
            String accountMoneyStr = depotHead.getAccountMoneyList().replace("[", "").replace("]", "").replaceAll("\"", "");
            String[] split = accountMoneyStr.split(",");
            BigDecimal accountMoneySum = BigDecimal.ZERO;
            for (String s:
                    split) {
                accountMoneySum.add(new BigDecimal(s));
            }
            accountMoneySum = accountMoneySum.abs();
            if(!accountMoneySum.equals(depotHead.getChangeAmount().abs())){
                throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_MANY_ACCOUNT_FAILED_MSG);
            }
            depotHead.setAccountMoneyList(accountMoneyStr);
        }
        //校验累计扣除订金是否超出订单中的金额
        if(depotHead.getDeposit() != null && StringUtils.hasText(depotHead.getLinkNumber())){
            BigDecimal deposit = depotHeadMapper.getDepositByLinkNumberAndNumber(depotHead.getLinkNumber(), depotHead.getNumber());
            BigDecimal changeAmount = depotHeadMapper.selectOne(new QueryWrapper<DepotHead>().eq("number", depotHead.getLinkNumber())).getChangeAmount();
            if (changeAmount != null) {
                changeAmount = changeAmount.abs();
                if(depotHead.getDeposit().add(deposit).compareTo(changeAmount) > 0){
                    throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_DEPOSIT_OVER_PRE_MSG);
                }
            }
        }
        depotHeadMapper.updateById(depotHead);
        //入库和出库处理预付款信息
        if("预付款".equals(depotHead.getPayType()) && depotHead.getOrganId() != null){
            Supplier supplier = supplierService.getById(depotHead.getOrganId());
            BigDecimal supplierAdvanceIn = supplier.getAdvanceIn();
            if(supplierAdvanceIn.compareTo(depotHead.getTotalPrice()) >= 0){
                supplier.setAdvanceIn(supplierAdvanceIn.subtract(
                        depotHead.getTotalPrice()).add(originalDh.getTotalPrice()));
                supplierService.updateById(supplier);
            }else{
                throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_MEMBER_PAY_LACK_MSG);
            }
        }
        depotItemService.saveDetail(rows, depotHead, "update", token);
        //日志数据补充
    }

    @Override
    public  int delete(Long id, String token){

        return deleteBatchIds(id.toString(), token);
    }

    @Override
    public int deleteBatch(String ids, String token){
        return deleteBatchIds(ids, token);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBatchIds(String ids, String token){
        StringBuilder sb = new StringBuilder();
        sb.append("删除");
        List<Long> idNums = StringUtil.strToLongList(ids);
        List<DepotHead> depotHeadList = depotHeadMapper.selectBatchIds(idNums);
        User user = userService.getLoginUser(token);
        for (DepotHead depotHead:
             depotHeadList) {
            sb.append("[").append(depotHead.getNumber()).append("]");
            //未审核单据才能删除
            if("0".equals(depotHead.getStatus())){
                if("出库".equals(depotHead.getType()) &&
                        !BusinessConstant.SUB_TYPE_TRANSFER.equals(depotHead.getSubType())){
                    List<DepotItem> depotItemList = depotItemService.getDepotItemListByHidHasSNumber(
                            depotHead.getId(), BusinessConstant.ENABLE_SERIAL_NUMBER_ENABLED);
                    for (DepotItem depotItem:
                         depotItemList) {
                        serialNumberService.updateByOutBillNoAndMId(depotItem.getMaterialId(), depotHead.getNumber(), depotItem.getBasicNumber(), user);
                    }
                }
                //对于零售出库单据，更新会员的预收款信息
                if("出库".equals(depotHead.getType()) &&
                        !BusinessConstant.SUB_TYPE_RETAIL.equals(depotHead.getSubType())){
                    if("预付款".equals(depotHead.getPayType()) && depotHead.getOrganId() != null){

                        supplierService.updateAdvanceInById(depotHead.getOrganId(), depotHead.getTotalPrice().abs());
                    }
                }
                //删除子单据并更新相关物料库存
                depotItemService.deleteDepotItemHeadId(depotHead.getId(), token);
                //删除单据
                depotHeadMapper.deleteBatchIds(idNums);
                if(StringUtils.hasText(depotHead.getLinkNumber())){
                    //将关联的单据置为审核状态-针对采购入库、销售出库和盘点复盘
                    if((BusinessConstant.TYPE_IN_DEPOT.equals(depotHead.getType()) && BusinessConstant.SUB_TYPE_PURCHASE.equals(depotHead.getSubType())) ||
                            (BusinessConstant.TYPE_OUT_DEPOT.equals(depotHead.getType()) && BusinessConstant.SUB_TYPE_SALES.equals(depotHead.getSubType())) ||
                            (BusinessConstant.TYPE_OTHER.equals(depotHead.getType()) && BusinessConstant.SUB_TYPE_SALES.equals(depotHead.getSubType()))){
                        updateDepotHeadStatusForPurIn(depotHead.getLinkNumber(), depotHead.getNumber(), depotHead.getType());
                    }
                    //将关联的销售订单单据置为未采购状态-针对销售订单转采购订单的情况
                    if(BusinessConstant.TYPE_OTHER.equals(depotHead.getType()) && BusinessConstant.SUB_TYPE_PURCHASE_ORDER.equals(depotHead.getSubType())){
                        updateDepotHeadStatusForPurOther(depotHead.getLinkNumber(), depotHead.getType());
                    }
                }

            }else{
                throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_UN_AUDIT_DELETE_FAILED_MSG);
            }
        }
        //日志数据增加
        return 1;
    }
    @Override
    public int updateDepotHeadStatusForPurIn(String linkNumber, String number, String type) {
        QueryWrapper<DepotHead> queryWrapper = new QueryWrapper<>();
        List<DepotHead> depotHeads = depotHeadMapper.selectList(queryWrapper.eq("link_number", linkNumber).ne("number", number).eq("type", type));
        String status = BusinessConstant.BILLS_STATUS_AUDIT;
        if(depotHeads != null && depotHeads.size() > 0){
            status = BusinessConstant.BILLS_STATUS_SKIPING;
        }
        UpdateWrapper<DepotHead> updateWrapper = new UpdateWrapper<DepotHead>().eq("number", linkNumber).set("status", status);
        return depotHeadMapper.update(null, updateWrapper);
    }

    @Override
    public int updateDepotHeadStatusForPurOther(String linkNumber, String type) {
        Map<Long, BigDecimal> materialSumMap = depotItemService.getDepotItemMaterialSum(linkNumber, type);
        String status = BusinessConstant.BILLS_STATUS_UN_AUDIT;
        if(materialSumMap != null && materialSumMap.size() > 0){
            status = BusinessConstant.BILLS_STATUS_SKIPING;
        }
        UpdateWrapper<DepotHead> updateWrapper = new UpdateWrapper<DepotHead>().eq("number", linkNumber).set("status", status);
        return depotHeadMapper.update(null, updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateStatus(String ids, String status, String token) {
        List<Long> idNums = StringUtil.strToLongList(ids);
        List<DepotHead> depotHeads = depotHeadMapper.selectBatchIds(idNums);
        for (DepotHead depotHead:
             depotHeads) {
            if(BusinessConstant.BILLS_STATUS_UN_AUDIT.equals(status)){
                if(!BusinessConstant.BILLS_STATUS_AUDIT.equals(depotHead.getStatus())){
                    throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_AUDIT_TO_UN_AUDIT_FAILED_MSG);
                }
            }else if(BusinessConstant.BILLS_STATUS_AUDIT.equals(status)){
                if(!BusinessConstant.BILLS_STATUS_UN_AUDIT.equals(depotHead.getStatus())){
                    throw new RuntimeException(ExceptionConstant.DEPOT_HEAD_UN_AUDIT_TO_AUDIT_FAILED_MSG);
                }
            }
        }
        UpdateWrapper<DepotHead> updateWrapper = new UpdateWrapper<>();
        int count = depotHeadMapper.update(null, updateWrapper.in("id", idNums).set("status", status));
        if (systemConfigController.getCurrent().getStockApprovalFlag().equals("1")) {
            for (Long id:
                 idNums) {
                List<DepotItem> depotItemList = depotItemService.getListByKey("header_id", id);
                for (DepotItem depotItem:
                     depotItemList) {
                    depotItemService.updateStockByDepotItem(depotItem, token);
                }
            }
        }
        return count;
    }

}




