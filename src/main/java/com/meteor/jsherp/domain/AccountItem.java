package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 财务子表
 * @TableName jsh_account_item
 */
@TableName(value ="jsh_account_item")
@Data
public class AccountItem implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 表头Id
     */
    private Long headerId;

    /**
     * 账户Id
     */
    private Long accountId;

    /**
     * 收支项目Id
     */
    private Long inOutItemId;

    /**
     * 单据id
     */
    private Long billId;

    /**
     * 应收欠款
     */
    private BigDecimal needDebt;

    /**
     * 已收欠款
     */
    private BigDecimal finishDebt;

    /**
     * 单项金额
     */
    private BigDecimal eachAmount;

    /**
     * 单据备注
     */
    private String remark;

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
        AccountItem other = (AccountItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHeaderId() == null ? other.getHeaderId() == null : this.getHeaderId().equals(other.getHeaderId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getInOutItemId() == null ? other.getInOutItemId() == null : this.getInOutItemId().equals(other.getInOutItemId()))
            && (this.getBillId() == null ? other.getBillId() == null : this.getBillId().equals(other.getBillId()))
            && (this.getNeedDebt() == null ? other.getNeedDebt() == null : this.getNeedDebt().equals(other.getNeedDebt()))
            && (this.getFinishDebt() == null ? other.getFinishDebt() == null : this.getFinishDebt().equals(other.getFinishDebt()))
            && (this.getEachAmount() == null ? other.getEachAmount() == null : this.getEachAmount().equals(other.getEachAmount()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHeaderId() == null) ? 0 : getHeaderId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getInOutItemId() == null) ? 0 : getInOutItemId().hashCode());
        result = prime * result + ((getBillId() == null) ? 0 : getBillId().hashCode());
        result = prime * result + ((getNeedDebt() == null) ? 0 : getNeedDebt().hashCode());
        result = prime * result + ((getFinishDebt() == null) ? 0 : getFinishDebt().hashCode());
        result = prime * result + ((getEachAmount() == null) ? 0 : getEachAmount().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
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
        sb.append(", headerId=").append(headerId);
        sb.append(", accountId=").append(accountId);
        sb.append(", inOutItemId=").append(inOutItemId);
        sb.append(", billId=").append(billId);
        sb.append(", needDebt=").append(needDebt);
        sb.append(", finishDebt=").append(finishDebt);
        sb.append(", eachAmount=").append(eachAmount);
        sb.append(", remark=").append(remark);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}