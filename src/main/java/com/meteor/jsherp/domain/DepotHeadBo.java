package com.meteor.jsherp.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Data
public class DepotHeadBo extends DepotHead{

    private String projectName;

    private String organName;

    private String userName;

    private String accountName;

    private String allocationProjectName;

    private String materialsList;

    private String salesManStr;

    private String operTimeStr;

    private BigDecimal finishDebt;

    private String depotHeadType;

    private String creatorName;

    private String contacts;

    private String telephone;

    private String address;

    private BigDecimal finishDeposit;

    private BigDecimal needDebt;

    private BigDecimal debt;

    private BigDecimal materialCount;

    /**
     * 是否有付款单或收款单
     */
    private Boolean hasFinancialFlag;
    /**
     * 是否有退款单
     */
    private Boolean hasBackFlag;

    /**
     * 实际欠款
     */
    private BigDecimal realNeedDebt;
}
