package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 多单位表
 * @TableName jsh_unit
 */
@TableName(value ="jsh_unit")
@Data
public class Unit implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称，支持多单位
     */
    private String name;

    /**
     * 基础单位
     */
    private String basicUnit;

    /**
     * 副单位
     */
    private String otherUnit;

    /**
     * 副单位2
     */
    private String otherUnitTwo;

    /**
     * 副单位3
     */
    private String otherUnitThree;

    /**
     * 比例
     */
    private BigDecimal ratio;

    /**
     * 比例2
     */
    private BigDecimal ratioTwo;

    /**
     * 比例3
     */
    private BigDecimal ratioThree;

    /**
     * 启用
     */
    private Boolean enabled;

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
        Unit other = (Unit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getBasicUnit() == null ? other.getBasicUnit() == null : this.getBasicUnit().equals(other.getBasicUnit()))
            && (this.getOtherUnit() == null ? other.getOtherUnit() == null : this.getOtherUnit().equals(other.getOtherUnit()))
            && (this.getOtherUnitTwo() == null ? other.getOtherUnitTwo() == null : this.getOtherUnitTwo().equals(other.getOtherUnitTwo()))
            && (this.getOtherUnitThree() == null ? other.getOtherUnitThree() == null : this.getOtherUnitThree().equals(other.getOtherUnitThree()))
            && (this.getRatio() == null ? other.getRatio() == null : this.getRatio().equals(other.getRatio()))
            && (this.getRatioTwo() == null ? other.getRatioTwo() == null : this.getRatioTwo().equals(other.getRatioTwo()))
            && (this.getRatioThree() == null ? other.getRatioThree() == null : this.getRatioThree().equals(other.getRatioThree()))
            && (this.getEnabled() == null ? other.getEnabled() == null : this.getEnabled().equals(other.getEnabled()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getBasicUnit() == null) ? 0 : getBasicUnit().hashCode());
        result = prime * result + ((getOtherUnit() == null) ? 0 : getOtherUnit().hashCode());
        result = prime * result + ((getOtherUnitTwo() == null) ? 0 : getOtherUnitTwo().hashCode());
        result = prime * result + ((getOtherUnitThree() == null) ? 0 : getOtherUnitThree().hashCode());
        result = prime * result + ((getRatio() == null) ? 0 : getRatio().hashCode());
        result = prime * result + ((getRatioTwo() == null) ? 0 : getRatioTwo().hashCode());
        result = prime * result + ((getRatioThree() == null) ? 0 : getRatioThree().hashCode());
        result = prime * result + ((getEnabled() == null) ? 0 : getEnabled().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", basicUnit=").append(basicUnit);
        sb.append(", otherUnit=").append(otherUnit);
        sb.append(", otherUnitTwo=").append(otherUnitTwo);
        sb.append(", otherUnitThree=").append(otherUnitThree);
        sb.append(", ratio=").append(ratio);
        sb.append(", ratioTwo=").append(ratioTwo);
        sb.append(", ratioThree=").append(ratioThree);
        sb.append(", enabled=").append(enabled);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}