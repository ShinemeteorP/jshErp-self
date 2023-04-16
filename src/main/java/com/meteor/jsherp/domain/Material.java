package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 产品表
 * @TableName jsh_material
 */
@TableName(value ="jsh_material")
@Data
public class Material implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品类型id
     */
    private Long categoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 制造商
     */
    private String mfrs;

    /**
     * 型号
     */
    private String model;

    /**
     * 规格
     */
    private String standard;

    /**
     * 颜色
     */
    private String color;

    /**
     * 单位-单个
     */
    private String unit;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片名称
     */
    private String imgName;

    /**
     * 计量单位Id
     */
    private Long unitId;

    /**
     * 保质期天数
     */
    private Integer expiryNum;

    /**
     * 基础重量(kg)
     */
    private BigDecimal weight;

    /**
     * 启用 0-禁用  1-启用
     */
    private Boolean enabled;

    /**
     * 自定义1
     */
    private String otherField1;

    /**
     * 自定义2
     */
    private String otherField2;

    /**
     * 自定义3
     */
    private String otherField3;

    /**
     * 是否开启序列号，0否，1是
     */
    private String enableSerialNumber;

    /**
     * 是否开启批号，0否，1是
     */
    private String enableBatchNumber;

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
        Material other = (Material) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMfrs() == null ? other.getMfrs() == null : this.getMfrs().equals(other.getMfrs()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getStandard() == null ? other.getStandard() == null : this.getStandard().equals(other.getStandard()))
            && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getImgName() == null ? other.getImgName() == null : this.getImgName().equals(other.getImgName()))
            && (this.getUnitId() == null ? other.getUnitId() == null : this.getUnitId().equals(other.getUnitId()))
            && (this.getExpiryNum() == null ? other.getExpiryNum() == null : this.getExpiryNum().equals(other.getExpiryNum()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getOtherField1() == null ? other.getOtherField1() == null : this.getOtherField1().equals(other.getOtherField1()))
            && (this.getOtherField2() == null ? other.getOtherField2() == null : this.getOtherField2().equals(other.getOtherField2()))
            && (this.getOtherField3() == null ? other.getOtherField3() == null : this.getOtherField3().equals(other.getOtherField3()))
            && (this.getEnableSerialNumber() == null ? other.getEnableSerialNumber() == null : this.getEnableSerialNumber().equals(other.getEnableSerialNumber()))
            && (this.getEnableBatchNumber() == null ? other.getEnableBatchNumber() == null : this.getEnableBatchNumber().equals(other.getEnableBatchNumber()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMfrs() == null) ? 0 : getMfrs().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getStandard() == null) ? 0 : getStandard().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getImgName() == null) ? 0 : getImgName().hashCode());
        result = prime * result + ((getUnitId() == null) ? 0 : getUnitId().hashCode());
        result = prime * result + ((getExpiryNum() == null) ? 0 : getExpiryNum().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
        result = prime * result + ((getOtherField1() == null) ? 0 : getOtherField1().hashCode());
        result = prime * result + ((getOtherField2() == null) ? 0 : getOtherField2().hashCode());
        result = prime * result + ((getOtherField3() == null) ? 0 : getOtherField3().hashCode());
        result = prime * result + ((getEnableSerialNumber() == null) ? 0 : getEnableSerialNumber().hashCode());
        result = prime * result + ((getEnableBatchNumber() == null) ? 0 : getEnableBatchNumber().hashCode());
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
        sb.append(", categoryId=").append(categoryId);
        sb.append(", name=").append(name);
        sb.append(", mfrs=").append(mfrs);
        sb.append(", model=").append(model);
        sb.append(", standard=").append(standard);
        sb.append(", color=").append(color);
        sb.append(", unit=").append(unit);
        sb.append(", remark=").append(remark);
        sb.append(", imgName=").append(imgName);
        sb.append(", unitId=").append(unitId);
        sb.append(", expiryNum=").append(expiryNum);
        sb.append(", weight=").append(weight);
        sb.append(", enabled=").append(enabled);
        sb.append(", otherField1=").append(otherField1);
        sb.append(", otherField2=").append(otherField2);
        sb.append(", otherField3=").append(otherField3);
        sb.append(", enableSerialNumber=").append(enableSerialNumber);
        sb.append(", enableBatchNumber=").append(enableBatchNumber);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}