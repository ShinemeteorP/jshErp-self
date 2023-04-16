package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 系统参数
 * @TableName jsh_system_config
 */
@TableName(value ="jsh_system_config")
@Data
public class SystemConfig implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司联系人
     */
    private String companyContacts;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 公司电话
     */
    private String companyTel;

    /**
     * 公司传真
     */
    private String companyFax;

    /**
     * 公司邮编
     */
    private String companyPostCode;

    /**
     * 销售协议
     */
    private String saleAgreement;

    /**
     * 仓库启用标记，0未启用，1启用
     */
    private String depotFlag;

    /**
     * 客户启用标记，0未启用，1启用
     */
    private String customerFlag;

    /**
     * 负库存启用标记，0未启用，1启用
     */
    private String minusStockFlag;

    /**
     * 以销定购启用标记，0未启用，1启用
     */
    private String purchaseBySaleFlag;

    /**
     * 多级审核启用标记，0未启用，1启用
     */
    private String multiLevelApprovalFlag;

    /**
     * 流程类型，可多选
     */
    private String multiBillType;

    /**
     * 金额审核启用标记，0未启用，1启用
     */
    private String amountApprovalFlag;

    /**
     * 库存审核启用标记，0未启用，1启用
     */
    private String stockApprovalFlag;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 删除标记，0未删除，1删除
     */
    private String deleteFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SystemConfig other = (SystemConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCompanyName() == null ? other.getCompanyName() == null : this.getCompanyName().equals(other.getCompanyName()))
            && (this.getCompanyContacts() == null ? other.getCompanyContacts() == null : this.getCompanyContacts().equals(other.getCompanyContacts()))
            && (this.getCompanyAddress() == null ? other.getCompanyAddress() == null : this.getCompanyAddress().equals(other.getCompanyAddress()))
            && (this.getCompanyTel() == null ? other.getCompanyTel() == null : this.getCompanyTel().equals(other.getCompanyTel()))
            && (this.getCompanyFax() == null ? other.getCompanyFax() == null : this.getCompanyFax().equals(other.getCompanyFax()))
            && (this.getCompanyPostCode() == null ? other.getCompanyPostCode() == null : this.getCompanyPostCode().equals(other.getCompanyPostCode()))
            && (this.getSaleAgreement() == null ? other.getSaleAgreement() == null : this.getSaleAgreement().equals(other.getSaleAgreement()))
            && (this.getDepotFlag() == null ? other.getDepotFlag() == null : this.getDepotFlag().equals(other.getDepotFlag()))
            && (this.getCustomerFlag() == null ? other.getCustomerFlag() == null : this.getCustomerFlag().equals(other.getCustomerFlag()))
            && (this.getMinusStockFlag() == null ? other.getMinusStockFlag() == null : this.getMinusStockFlag().equals(other.getMinusStockFlag()))
            && (this.getPurchaseBySaleFlag() == null ? other.getPurchaseBySaleFlag() == null : this.getPurchaseBySaleFlag().equals(other.getPurchaseBySaleFlag()))
            && (this.getMultiLevelApprovalFlag() == null ? other.getMultiLevelApprovalFlag() == null : this.getMultiLevelApprovalFlag().equals(other.getMultiLevelApprovalFlag()))
            && (this.getMultiBillType() == null ? other.getMultiBillType() == null : this.getMultiBillType().equals(other.getMultiBillType()))
            && (this.getAmountApprovalFlag() == null ? other.getAmountApprovalFlag() == null : this.getAmountApprovalFlag().equals(other.getAmountApprovalFlag()))
            && (this.getStockApprovalFlag() == null ? other.getStockApprovalFlag() == null : this.getStockApprovalFlag().equals(other.getStockApprovalFlag()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCompanyName() == null) ? 0 : getCompanyName().hashCode());
        result = prime * result + ((getCompanyContacts() == null) ? 0 : getCompanyContacts().hashCode());
        result = prime * result + ((getCompanyAddress() == null) ? 0 : getCompanyAddress().hashCode());
        result = prime * result + ((getCompanyTel() == null) ? 0 : getCompanyTel().hashCode());
        result = prime * result + ((getCompanyFax() == null) ? 0 : getCompanyFax().hashCode());
        result = prime * result + ((getCompanyPostCode() == null) ? 0 : getCompanyPostCode().hashCode());
        result = prime * result + ((getSaleAgreement() == null) ? 0 : getSaleAgreement().hashCode());
        result = prime * result + ((getDepotFlag() == null) ? 0 : getDepotFlag().hashCode());
        result = prime * result + ((getCustomerFlag() == null) ? 0 : getCustomerFlag().hashCode());
        result = prime * result + ((getMinusStockFlag() == null) ? 0 : getMinusStockFlag().hashCode());
        result = prime * result + ((getPurchaseBySaleFlag() == null) ? 0 : getPurchaseBySaleFlag().hashCode());
        result = prime * result + ((getMultiLevelApprovalFlag() == null) ? 0 : getMultiLevelApprovalFlag().hashCode());
        result = prime * result + ((getMultiBillType() == null) ? 0 : getMultiBillType().hashCode());
        result = prime * result + ((getAmountApprovalFlag() == null) ? 0 : getAmountApprovalFlag().hashCode());
        result = prime * result + ((getStockApprovalFlag() == null) ? 0 : getStockApprovalFlag().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyName=").append(companyName);
        sb.append(", companyContacts=").append(companyContacts);
        sb.append(", companyAddress=").append(companyAddress);
        sb.append(", companyTel=").append(companyTel);
        sb.append(", companyFax=").append(companyFax);
        sb.append(", companyPostCode=").append(companyPostCode);
        sb.append(", saleAgreement=").append(saleAgreement);
        sb.append(", depotFlag=").append(depotFlag);
        sb.append(", customerFlag=").append(customerFlag);
        sb.append(", minusStockFlag=").append(minusStockFlag);
        sb.append(", purchaseBySaleFlag=").append(purchaseBySaleFlag);
        sb.append(", multiLevelApprovalFlag=").append(multiLevelApprovalFlag);
        sb.append(", multiBillType=").append(multiBillType);
        sb.append(", amountApprovalFlag=").append(amountApprovalFlag);
        sb.append(", stockApprovalFlag=").append(stockApprovalFlag);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}