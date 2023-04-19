package com.meteor.jsherp.constant;

/**
 * @author 刘鑫
 * @version 1.0
 */
public interface BusinessConstant {

    /**
     * 系统配置的各项功能如：多级审核机制、金额审核机制、以销定购机制的开启标识，均是0标识未启用，1标识启用
     */
    String SYSTEM_CONFIG_APPROVAL_CLOSE= "0";
    String SYSTEM_CONFIG_APPROVAL_OPEN = "1";

    /**
     * 默认的日期格式
     */
    String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 一天的初始时间
     */
    String DAY_FIRST_TIME = " 00:00:00";
    /**
     * 一天的结束时间
     */
    String DAY_LAST_TIME = " 23:59:59";

    /**
     * 用户的价格屏蔽选项
     * 1  屏蔽采购价
     * 2  屏蔽零售价
     * 3  排布销售价
     */
    String SHIELD_BUY_PRICE = "1";
    String SHIELD_RETAIL_SALE_PRICE = "2";
    String SHIELD_SALE_PRICE = "3";
    String PRICE_SHIELD_MARK = "***";

    /**
     * 今天、昨天、月、年的销售、采购、零售销售的销售额对应key的名称
     */
    String TODAY_BUY = "todayBuy";
    String TODAY_SALE = "todaySale";
    String TODAY_RETAIL_SALE = "todayRetailSale";

    String YESTERDAY_BUY = "yesterdayBuy";
    String YESTERDAY_SALE = "yesterdaySale";
    String YESTERDAY_RETAIL_SALE = "yesterdayRetailSale";

    String MONTH_BUY = "monthBuy";
    String MONTH_SALE = "monthSale";
    String MONTH_RETAIL_SALE = "monthRetailSale";

    String YEAR_BUY = "yearBuy";
    String YEAR_SALE = "yearSale";
    String YEAR_RETAIL_SALE = "yearRetailSale";
}
