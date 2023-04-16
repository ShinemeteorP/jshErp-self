package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 产品价格扩展
 * @TableName jsh_material_extend
 */
@TableName(value ="jsh_material_extend")
@Data
public class MaterialExtend implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private Long materialId;

    /**
     * 商品条码
     */
    private String barCode;

    /**
     * 商品单位
     */
    private String commodityUnit;

    /**
     * 多属性
     */
    private String sku;

    /**
     * 采购价格
     */
    private BigDecimal purchaseDecimal;

    /**
     * 零售价格
     */
    private BigDecimal commodityDecimal;

    /**
     * 销售价格
     */
    private BigDecimal wholesaleDecimal;

    /**
     * 最低售价
     */
    private BigDecimal lowDecimal;

    /**
     * 是否为默认单位，1是，0否
     */
    private String defaultFlag;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 创建人编码
     */
    private String createSerial;

    /**
     * 更新人编码
     */
    private String updateSerial;

    /**
     * 更新时间戳
     */
    private Long updateTime;

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
        MaterialExtend other = (MaterialExtend) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMaterialId() == null ? other.getMaterialId() == null : this.getMaterialId().equals(other.getMaterialId()))
            && (this.getBarCode() == null ? other.getBarCode() == null : this.getBarCode().equals(other.getBarCode()))
            && (this.getCommodityUnit() == null ? other.getCommodityUnit() == null : this.getCommodityUnit().equals(other.getCommodityUnit()))
            && (this.getSku() == null ? other.getSku() == null : this.getSku().equals(other.getSku()))
            && (this.getPurchaseDecimal() == null ? other.getPurchaseDecimal() == null : this.getPurchaseDecimal().equals(other.getPurchaseDecimal()))
            && (this.getCommodityDecimal() == null ? other.getCommodityDecimal() == null : this.getCommodityDecimal().equals(other.getCommodityDecimal()))
            && (this.getWholesaleDecimal() == null ? other.getWholesaleDecimal() == null : this.getWholesaleDecimal().equals(other.getWholesaleDecimal()))
            && (this.getLowDecimal() == null ? other.getLowDecimal() == null : this.getLowDecimal().equals(other.getLowDecimal()))
            && (this.getDefaultFlag() == null ? other.getDefaultFlag() == null : this.getDefaultFlag().equals(other.getDefaultFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateSerial() == null ? other.getCreateSerial() == null : this.getCreateSerial().equals(other.getCreateSerial()))
            && (this.getUpdateSerial() == null ? other.getUpdateSerial() == null : this.getUpdateSerial().equals(other.getUpdateSerial()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMaterialId() == null) ? 0 : getMaterialId().hashCode());
        result = prime * result + ((getBarCode() == null) ? 0 : getBarCode().hashCode());
        result = prime * result + ((getCommodityUnit() == null) ? 0 : getCommodityUnit().hashCode());
        result = prime * result + ((getSku() == null) ? 0 : getSku().hashCode());
        result = prime * result + ((getPurchaseDecimal() == null) ? 0 : getPurchaseDecimal().hashCode());
        result = prime * result + ((getCommodityDecimal() == null) ? 0 : getCommodityDecimal().hashCode());
        result = prime * result + ((getWholesaleDecimal() == null) ? 0 : getWholesaleDecimal().hashCode());
        result = prime * result + ((getLowDecimal() == null) ? 0 : getLowDecimal().hashCode());
        result = prime * result + ((getDefaultFlag() == null) ? 0 : getDefaultFlag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateSerial() == null) ? 0 : getCreateSerial().hashCode());
        result = prime * result + ((getUpdateSerial() == null) ? 0 : getUpdateSerial().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
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
        sb.append(", barCode=").append(barCode);
        sb.append(", commodityUnit=").append(commodityUnit);
        sb.append(", sku=").append(sku);
        sb.append(", purchaseDecimal=").append(purchaseDecimal);
        sb.append(", commodityDecimal=").append(commodityDecimal);
        sb.append(", wholesaleDecimal=").append(wholesaleDecimal);
        sb.append(", lowDecimal=").append(lowDecimal);
        sb.append(", defaultFlag=").append(defaultFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", createSerial=").append(createSerial);
        sb.append(", updateSerial=").append(updateSerial);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}