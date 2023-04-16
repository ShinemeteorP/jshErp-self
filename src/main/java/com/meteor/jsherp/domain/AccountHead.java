package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 财务主表
 * @TableName jsh_account_head
 */
@TableName(value ="jsh_account_head")
@Data
public class AccountHead implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类型(支出/收入/收款/付款/转账)
     */
    private String type;

    /**
     * 单位Id(收款/付款单位)
     */
    private Long organId;

    /**
     * 经手人id
     */
    private Long handsPersonId;

    /**
     * 操作员
     */
    private Long creator;

    /**
     * 变动金额(优惠/收款/付款/实付)
     */
    private BigDecimal changeAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountMoney;

    /**
     * 合计金额
     */
    private BigDecimal totalPrice;

    /**
     * 账户(收款/付款)
     */
    private Long accountId;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 单据日期
     */
    private Date billTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 附件名称
     */
    private String fileName;

    /**
     * 状态，0未审核、1已审核、9审核中
     */
    private String status;

    /**
     * 单据来源，0-pc，1-手机
     */
    private String source;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 删除标记，0未删除，1删除
     */
    @TableLogic
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
        AccountHead other = (AccountHead) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getOrganId() == null ? other.getOrganId() == null : this.getOrganId().equals(other.getOrganId()))
            && (this.getHandsPersonId() == null ? other.getHandsPersonId() == null : this.getHandsPersonId().equals(other.getHandsPersonId()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getChangeAmount() == null ? other.getChangeAmount() == null : this.getChangeAmount().equals(other.getChangeAmount()))
            && (this.getDiscountMoney() == null ? other.getDiscountMoney() == null : this.getDiscountMoney().equals(other.getDiscountMoney()))
            && (this.getTotalPrice() == null ? other.getTotalPrice() == null : this.getTotalPrice().equals(other.getTotalPrice()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getBillNo() == null ? other.getBillNo() == null : this.getBillNo().equals(other.getBillNo()))
            && (this.getBillTime() == null ? other.getBillTime() == null : this.getBillTime().equals(other.getBillTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getOrganId() == null) ? 0 : getOrganId().hashCode());
        result = prime * result + ((getHandsPersonId() == null) ? 0 : getHandsPersonId().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getChangeAmount() == null) ? 0 : getChangeAmount().hashCode());
        result = prime * result + ((getDiscountMoney() == null) ? 0 : getDiscountMoney().hashCode());
        result = prime * result + ((getTotalPrice() == null) ? 0 : getTotalPrice().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getBillNo() == null) ? 0 : getBillNo().hashCode());
        result = prime * result + ((getBillTime() == null) ? 0 : getBillTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
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
        sb.append(", type=").append(type);
        sb.append(", organId=").append(organId);
        sb.append(", handsPersonId=").append(handsPersonId);
        sb.append(", creator=").append(creator);
        sb.append(", changeAmount=").append(changeAmount);
        sb.append(", discountMoney=").append(discountMoney);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", accountId=").append(accountId);
        sb.append(", billNo=").append(billNo);
        sb.append(", billTime=").append(billTime);
        sb.append(", remark=").append(remark);
        sb.append(", fileName=").append(fileName);
        sb.append(", status=").append(status);
        sb.append(", source=").append(source);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}