package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 单据主表
 * @TableName jsh_depot_head
 */
@TableName(value ="jsh_depot_head")
@Data
public class DepotHead implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类型(出库/入库)
     */
    private String type;

    /**
     * 出入库分类
     */
    private String subType;

    /**
     * 初始票据号
     */
    private String defaultNumber;

    /**
     * 票据号
     */
    private String number;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 出入库时间
     */
    private Date operTime;

    /**
     * 供应商id
     */
    private Long organId;

    /**
     * 操作员
     */
    private Long creator;

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 变动金额(收款/付款)
     */
    private BigDecimal changeAmount;

    /**
     * 找零金额
     */
    private BigDecimal backAmount;

    /**
     * 合计金额
     */
    private BigDecimal totalPrice;

    /**
     * 付款类型(现金、记账等)
     */
    private String payType;

    /**
     * 单据类型
     */
    private String billType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 附件名称
     */
    private String fileName;

    /**
     * 业务员（可以多个）
     */
    private String salesMan;

    /**
     * 多账户ID列表
     */
    private String accountIdList;

    /**
     * 多账户金额列表
     */
    private String accountMoneyList;

    /**
     * 优惠率
     */
    private BigDecimal discount;

    /**
     * 优惠金额
     */
    private BigDecimal discountMoney;

    /**
     * 优惠后金额
     */
    private BigDecimal discountLastMoney;

    /**
     * 销售或采购费用合计
     */
    private BigDecimal otherMoney;

    /**
     * 订金
     */
    private BigDecimal deposit;

    /**
     * 状态，0未审核、1已审核、2完成采购|销售、3部分采购|销售、9审核中
     */
    private String status;

    /**
     * 采购状态，0未采购、2完成采购、3部分采购
     */
    private String purchaseStatus;

    /**
     * 单据来源，0-pc，1-手机
     */
    private String source;

    /**
     * 关联订单号
     */
    private String linkNumber;

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
        DepotHead other = (DepotHead) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getSubType() == null ? other.getSubType() == null : this.getSubType().equals(other.getSubType()))
            && (this.getDefaultNumber() == null ? other.getDefaultNumber() == null : this.getDefaultNumber().equals(other.getDefaultNumber()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getOperTime() == null ? other.getOperTime() == null : this.getOperTime().equals(other.getOperTime()))
            && (this.getOrganId() == null ? other.getOrganId() == null : this.getOrganId().equals(other.getOrganId()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getChangeAmount() == null ? other.getChangeAmount() == null : this.getChangeAmount().equals(other.getChangeAmount()))
            && (this.getBackAmount() == null ? other.getBackAmount() == null : this.getBackAmount().equals(other.getBackAmount()))
            && (this.getTotalPrice() == null ? other.getTotalPrice() == null : this.getTotalPrice().equals(other.getTotalPrice()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getBillType() == null ? other.getBillType() == null : this.getBillType().equals(other.getBillType()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getSalesMan() == null ? other.getSalesMan() == null : this.getSalesMan().equals(other.getSalesMan()))
            && (this.getAccountIdList() == null ? other.getAccountIdList() == null : this.getAccountIdList().equals(other.getAccountIdList()))
            && (this.getAccountMoneyList() == null ? other.getAccountMoneyList() == null : this.getAccountMoneyList().equals(other.getAccountMoneyList()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getDiscountMoney() == null ? other.getDiscountMoney() == null : this.getDiscountMoney().equals(other.getDiscountMoney()))
            && (this.getDiscountLastMoney() == null ? other.getDiscountLastMoney() == null : this.getDiscountLastMoney().equals(other.getDiscountLastMoney()))
            && (this.getOtherMoney() == null ? other.getOtherMoney() == null : this.getOtherMoney().equals(other.getOtherMoney()))
            && (this.getDeposit() == null ? other.getDeposit() == null : this.getDeposit().equals(other.getDeposit()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPurchaseStatus() == null ? other.getPurchaseStatus() == null : this.getPurchaseStatus().equals(other.getPurchaseStatus()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getLinkNumber() == null ? other.getLinkNumber() == null : this.getLinkNumber().equals(other.getLinkNumber()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getSubType() == null) ? 0 : getSubType().hashCode());
        result = prime * result + ((getDefaultNumber() == null) ? 0 : getDefaultNumber().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getOperTime() == null) ? 0 : getOperTime().hashCode());
        result = prime * result + ((getOrganId() == null) ? 0 : getOrganId().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getChangeAmount() == null) ? 0 : getChangeAmount().hashCode());
        result = prime * result + ((getBackAmount() == null) ? 0 : getBackAmount().hashCode());
        result = prime * result + ((getTotalPrice() == null) ? 0 : getTotalPrice().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getBillType() == null) ? 0 : getBillType().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getSalesMan() == null) ? 0 : getSalesMan().hashCode());
        result = prime * result + ((getAccountIdList() == null) ? 0 : getAccountIdList().hashCode());
        result = prime * result + ((getAccountMoneyList() == null) ? 0 : getAccountMoneyList().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getDiscountMoney() == null) ? 0 : getDiscountMoney().hashCode());
        result = prime * result + ((getDiscountLastMoney() == null) ? 0 : getDiscountLastMoney().hashCode());
        result = prime * result + ((getOtherMoney() == null) ? 0 : getOtherMoney().hashCode());
        result = prime * result + ((getDeposit() == null) ? 0 : getDeposit().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPurchaseStatus() == null) ? 0 : getPurchaseStatus().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getLinkNumber() == null) ? 0 : getLinkNumber().hashCode());
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
        sb.append(", subType=").append(subType);
        sb.append(", defaultNumber=").append(defaultNumber);
        sb.append(", number=").append(number);
        sb.append(", createTime=").append(createTime);
        sb.append(", operTime=").append(operTime);
        sb.append(", organId=").append(organId);
        sb.append(", creator=").append(creator);
        sb.append(", accountId=").append(accountId);
        sb.append(", changeAmount=").append(changeAmount);
        sb.append(", backAmount=").append(backAmount);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", payType=").append(payType);
        sb.append(", billType=").append(billType);
        sb.append(", remark=").append(remark);
        sb.append(", fileName=").append(fileName);
        sb.append(", salesMan=").append(salesMan);
        sb.append(", accountIdList=").append(accountIdList);
        sb.append(", accountMoneyList=").append(accountMoneyList);
        sb.append(", discount=").append(discount);
        sb.append(", discountMoney=").append(discountMoney);
        sb.append(", discountLastMoney=").append(discountLastMoney);
        sb.append(", otherMoney=").append(otherMoney);
        sb.append(", deposit=").append(deposit);
        sb.append(", status=").append(status);
        sb.append(", purchaseStatus=").append(purchaseStatus);
        sb.append(", source=").append(source);
        sb.append(", linkNumber=").append(linkNumber);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}