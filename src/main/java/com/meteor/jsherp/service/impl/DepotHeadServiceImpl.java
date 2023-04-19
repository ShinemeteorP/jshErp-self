package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.domain.DepotHead;
import com.meteor.jsherp.domain.Role;
import com.meteor.jsherp.mapper.DepotHeadMapper;
import com.meteor.jsherp.service.DepotHeadService;
import com.meteor.jsherp.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_depot_head(单据主表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:54
*/
@Service
public class DepotHeadServiceImpl extends ServiceImpl<DepotHeadMapper, DepotHead>
    implements DepotHeadService {

    @Resource
    private DepotHeadMapper depotHeadMapper;

    @Resource
    private RoleService roleService;

    @Override
    public Map<String, Object> getBuyAndSaleStatistics(long[] userIds, long userId, String approval) {
        String priceLimit = roleService.getRoleByUserId(userId).getPriceLimit();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BusinessConstant.DEFAULT_DATETIME_FORMAT);
        HashMap<String, Object> staticsMap = new HashMap<>();
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());
        LocalDate lastDayOfYear = now.with(TemporalAdjusters.lastDayOfYear());
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

}




