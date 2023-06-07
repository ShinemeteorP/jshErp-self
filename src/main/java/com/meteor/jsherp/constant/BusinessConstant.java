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

    String DEPOT_NUMBER_SEQ = "depot_number_seq";

    /**
     * * 是否卖出 isSell  '0'未卖出 '1'已卖出
     * */
    String IS_SELL_SELLED = "1";
    String IS_SELL_HOLD = "0";
    /**
     * 商品是否开启序列号标识enableSerialNumber  '0'未启用 '1'启用
     * */
    String ENABLE_SERIAL_NUMBER_ENABLED = "1";
    String ENABLE_SERIAL_NUMBER_NOT_ENABLED = "0";
    /**
     * 商品是否开启批号标识enableBatchNumber  '0'未启用 '1'启用
     * */
    String ENABLE_BATCH_NUMBER_ENABLED = "1";
    String ENABLE_BATCH_NUMBER_NOT_ENABLED = "0";

    /**
     * 单据状态 billsStatus '0'未审核 '1'审核 '2'完成采购|销售 '3'部分采购|销售
     * */
    String BILLS_STATUS_UN_AUDIT = "0";
    String BILLS_STATUS_AUDIT = "1";
    String BILLS_STATUS_SKIPED = "2";
    String BILLS_STATUS_SKIPING = "3";
    /**
     * 单据-采购状态 purchaseStatus '0'未采购、'2'完成采购、'3'部分采购
     * */
    String PURCHASE_STATUS_UN_AUDIT = "0";
    String PURCHASE_STATUS_SKIPED = "2";
    String PURCHASE_STATUS_SKIPING = "3";

    /**
     * 单据大类：入库、出库、其它
     */
    String TYPE_IN_DEPOT = "入库";
    String TYPE_OUT_DEPOT = "出库";
    String TYPE_OTHER = "其它";
    /**
     * 出入库分类
     *采购、采购退货、其它、零售、销售、调拨、盘点复盘等
     * */
    String SUB_TYPE_PURCHASE_ORDER = "采购订单";
    String SUB_TYPE_PURCHASE = "采购";
    String SUB_TYPE_PURCHASE_RETURN = "采购退货";
    String SUB_TYPE_OTHER = "其它";
    String SUB_TYPE_RETAIL = "零售";
    String SUB_TYPE_RETAIL_RETURN = "零售退货";
    String SUB_TYPE_SALES_ORDER = "销售订单";
    String SUB_TYPE_SALES = "销售";
    String SUB_TYPE_SALES_RETURN = "销售退货";
    String SUB_TYPE_TRANSFER = "调拨";
    String SUB_TYPE_CHECK_ENTER = "盘点录入";
    String SUB_TYPE_REPLAY = "盘点复盘";
    String SUB_TYPE_ASSEMBLE = "组装单";
    String SUB_TYPE_DISASSEMBLE = "拆卸单";
    /**
     * 批量插入sql时最大的数据条数
     * */
    int BATCH_INSERT_MAX_NUMBER = 500;
    /**
     * sequence名称
     * */
    //sequence返回字符串的最小长度
    Long SEQ_TO_STRING_MIN_LENGTH = 10000000000L;
    //sequence长度小于基准长度时前追加基础值
    String SEQ_TO_STRING_LESS_INSERT = "0";
    /**
     * 商品类别根目录id
     * */
    /**
     * create by: qiankunpingtai
     * create time: 2019/3/14 11:41
     * description:
     * 为了使用户可以自己建初始目录，设定根目录的父级目录id为-1
     *
     */
    Long MATERIAL_CATEGORY_ROOT_PARENT_ID = -1L;
    /**
     * 商品类别状态
     * 0系统默认，1启用，2删除
     * */
    String MATERIAL_CATEGORY_STATUS_DEFAULT = "0";
    String MATERIAL_CATEGORY_STATUS_ENABLE = "1";
    String MATERIAL_CATEGORY_STATUS_DELETE = "2";
    /**
     * 机构状态
     *  1未营业、2正常营业、3暂停营业、4终止营业,5已除名
     * */
    String ORGANIZATION_STCD_NOT_OPEN = "1";
    String ORGANIZATION_STCD_OPEN = "2";
    String ORGANIZATION_STCD_BUSINESS_SUSPENDED = "3";
    String ORGANIZATION_STCD_BUSINESS_TERMINATED = "4";
    String ORGANIZATION_STCD_REMOVED = "5";
    /**
     * 根机构父级编号
     * 根机父级构编号默认为-1
     * */
    String ORGANIZATION_ROOT_PARENT_NO = "-1";
    /**
     * 新增用户默认密码
     * */
    String USER_DEFAULT_PASSWORD = "123456";
    /**
     * 用户是否系统自带
     * 0、非系统自带，1系统自带
     * */
    byte USER_NOT_SYSTEM = 0;
    byte USER_IS_SYSTEM = 1;
    /**
     * 用户是否为管理者
     * 0、管理者，1员工
     * */
    byte USER_IS_MANAGER = 0;
    byte USER_NOT_MANAGER = 1;
    /**
     * 用户状态
     * 0：正常，1：删除，2封禁
     * */
    byte USER_STATUS_NORMAL = 0;
    byte USER_STATUS_DELETE = 1;
    byte USER_STATUS_BANNED = 2;
    /**
     * 日志操作
     * 新增、修改、删除、登录、导入
     * */
    String LOG_OPERATION_TYPE_ADD = "新增";
    String LOG_OPERATION_TYPE_BATCH_ADD = "批量新增";
    String LOG_OPERATION_TYPE_EDIT = "修改";
    String LOG_OPERATION_TYPE_DELETE = "删除";
    String LOG_OPERATION_TYPE_LOGIN = "登录";
    String LOG_OPERATION_TYPE_IMPORT = "导入";
    String LOG_OPERATION_TYPE_ENABLED = "更新状态";

    /**
     * 数据数量单位
     * 条
     * */
    String LOG_DATA_UNIT = "条";

    /**
     * 删除类型
     * 1正常删除
     * 2强制删除
     * */
    String DELETE_TYPE_NORMAL = "1";
    String DELETE_TYPE_FORCE = "2";

    /**
     * 默认管理员账号
     */
    String DEFAULT_MANAGER = "admin";

    String ROLE_TYPE_PRIVATE = "个人数据";

    String ROLE_TYPE_THIS_ORG = "本机构数据";
}
