package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 产品初始库存
 * @TableName jsh_material_initial_stock
 */
@TableName(value ="jsh_material_initial_stock")
@Data
public class MaterialInitialStock implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品id
     */
    private Long materialId;

    /**
     * 仓库id
     */
    private Long depotId;

    /**
     * 初始库存数量
     */
    private BigDecimal number;

    /**
     * 最低库存数量
     */
    private BigDecimal lowSafeStock;

    /**
     * 最高库存数量
     */
    private BigDecimal highSafeStock;

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
        MaterialInitialStock other = (MaterialInitialStock) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMaterialId() == null ? other.getMaterialId() == null : this.getMaterialId().equals(other.getMaterialId()))
            && (this.getDepotId() == null ? other.getDepotId() == null : this.getDepotId().equals(other.getDepotId()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getLowSafeStock() == null ? other.getLowSafeStock() == null : this.getLowSafeStock().equals(other.getLowSafeStock()))
            && (this.getHighSafeStock() == null ? other.getHighSafeStock() == null : this.getHighSafeStock().equals(other.getHighSafeStock()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMaterialId() == null) ? 0 : getMaterialId().hashCode());
        result = prime * result + ((getDepotId() == null) ? 0 : getDepotId().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        result = prime * result + ((getLowSafeStock() == null) ? 0 : getLowSafeStock().hashCode());
        result = prime * result + ((getHighSafeStock() == null) ? 0 : getHighSafeStock().hashCode());
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
        sb.append(", materialId=").append(materialId);
        sb.append(", depotId=").append(depotId);
        sb.append(", number=").append(number);
        sb.append(", lowSafeStock=").append(lowSafeStock);
        sb.append(", highSafeStock=").append(highSafeStock);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}