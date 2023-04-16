package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 供应商/客户信息表
 * @TableName jsh_supplier
 */
@TableName(value ="jsh_supplier")
@Data
public class Supplier implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 供应商名称
     */
    private String supplier;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String description;

    /**
     * 是否系统自带 0==系统 1==非系统
     */
    private Integer isystem;

    /**
     * 类型
     */
    private String type;

    /**
     * 启用
     */
    private Boolean enabled;

    /**
     * 预收款
     */
    private BigDecimal advanceIn;

    /**
     * 期初应收
     */
    private BigDecimal beginNeedGet;

    /**
     * 期初应付
     */
    private BigDecimal beginNeedPay;

    /**
     * 累计应收
     */
    private BigDecimal allNeedGet;

    /**
     * 累计应付
     */
    private BigDecimal allNeedPay;

    /**
     * 传真
     */
    private String fax;

    /**
     * 手机
     */
    private String telephone;

    /**
     * 地址
     */
    private String address;

    /**
     * 纳税人识别号
     */
    private String taxNum;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 排序
     */
    private String sort;

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
        Supplier other = (Supplier) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSupplier() == null ? other.getSupplier() == null : this.getSupplier().equals(other.getSupplier()))
            && (this.getContacts() == null ? other.getContacts() == null : this.getContacts().equals(other.getContacts()))
            && (this.getPhoneNum() == null ? other.getPhoneNum() == null : this.getPhoneNum().equals(other.getPhoneNum()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getIsystem() == null ? other.getIsystem() == null : this.getIsystem().equals(other.getIsystem()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getAdvanceIn() == null ? other.getAdvanceIn() == null : this.getAdvanceIn().equals(other.getAdvanceIn()))
            && (this.getBeginNeedGet() == null ? other.getBeginNeedGet() == null : this.getBeginNeedGet().equals(other.getBeginNeedGet()))
            && (this.getBeginNeedPay() == null ? other.getBeginNeedPay() == null : this.getBeginNeedPay().equals(other.getBeginNeedPay()))
            && (this.getAllNeedGet() == null ? other.getAllNeedGet() == null : this.getAllNeedGet().equals(other.getAllNeedGet()))
            && (this.getAllNeedPay() == null ? other.getAllNeedPay() == null : this.getAllNeedPay().equals(other.getAllNeedPay()))
            && (this.getFax() == null ? other.getFax() == null : this.getFax().equals(other.getFax()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getTaxNum() == null ? other.getTaxNum() == null : this.getTaxNum().equals(other.getTaxNum()))
            && (this.getBankName() == null ? other.getBankName() == null : this.getBankName().equals(other.getBankName()))
            && (this.getAccountNumber() == null ? other.getAccountNumber() == null : this.getAccountNumber().equals(other.getAccountNumber()))
            && (this.getTaxRate() == null ? other.getTaxRate() == null : this.getTaxRate().equals(other.getTaxRate()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSupplier() == null) ? 0 : getSupplier().hashCode());
        result = prime * result + ((getContacts() == null) ? 0 : getContacts().hashCode());
        result = prime * result + ((getPhoneNum() == null) ? 0 : getPhoneNum().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getIsystem() == null) ? 0 : getIsystem().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getAdvanceIn() == null) ? 0 : getAdvanceIn().hashCode());
        result = prime * result + ((getBeginNeedGet() == null) ? 0 : getBeginNeedGet().hashCode());
        result = prime * result + ((getBeginNeedPay() == null) ? 0 : getBeginNeedPay().hashCode());
        result = prime * result + ((getAllNeedGet() == null) ? 0 : getAllNeedGet().hashCode());
        result = prime * result + ((getAllNeedPay() == null) ? 0 : getAllNeedPay().hashCode());
        result = prime * result + ((getFax() == null) ? 0 : getFax().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getTaxNum() == null) ? 0 : getTaxNum().hashCode());
        result = prime * result + ((getBankName() == null) ? 0 : getBankName().hashCode());
        result = prime * result + ((getAccountNumber() == null) ? 0 : getAccountNumber().hashCode());
        result = prime * result + ((getTaxRate() == null) ? 0 : getTaxRate().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
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
        sb.append(", supplier=").append(supplier);
        sb.append(", contacts=").append(contacts);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", email=").append(email);
        sb.append(", description=").append(description);
        sb.append(", isystem=").append(isystem);
        sb.append(", type=").append(type);
        sb.append(", enabled=").append(enabled);
        sb.append(", advanceIn=").append(advanceIn);
        sb.append(", beginNeedGet=").append(beginNeedGet);
        sb.append(", beginNeedPay=").append(beginNeedPay);
        sb.append(", allNeedGet=").append(allNeedGet);
        sb.append(", allNeedPay=").append(allNeedPay);
        sb.append(", fax=").append(fax);
        sb.append(", telephone=").append(telephone);
        sb.append(", address=").append(address);
        sb.append(", taxNum=").append(taxNum);
        sb.append(", bankName=").append(bankName);
        sb.append(", accountNumber=").append(accountNumber);
        sb.append(", taxRate=").append(taxRate);
        sb.append(", sort=").append(sort);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}