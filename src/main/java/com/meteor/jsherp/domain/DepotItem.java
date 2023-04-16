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
 * 单据子表
 * @TableName jsh_depot_item
 */
@TableName(value ="jsh_depot_item")
@Data
public class DepotItem implements Serializable {
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
     * 商品Id
     */
    private Long materialId;

    /**
     * 商品扩展id
     */
    private Long materialExtendId;

    /**
     * 商品计量单位
     */
    private String materialUnit;

    /**
     * 多属性
     */
    private String sku;

    /**
     * 数量
     */
    private BigDecimal operNumber;

    /**
     * 基础数量，如kg、瓶
     */
    private BigDecimal basicNumber;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 采购单价
     */
    private BigDecimal purchaseUnitPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxUnitPrice;

    /**
     * 金额
     */
    private BigDecimal allPrice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 仓库ID
     */
    private Long depotId;

    /**
     * 调拨时，对方仓库Id
     */
    private Long anotherDepotId;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税额
     */
    private BigDecimal taxMoney;

    /**
     * 价税合计
     */
    private BigDecimal taxLastMoney;

    /**
     * 商品类型
     */
    private String materialType;

    /**
     * 序列号列表
     */
    private String snList;

    /**
     * 批号
     */
    private String batchNumber;

    /**
     * 有效日期
     */
    private Date expirationDate;

    /**
     * 关联明细id
     */
    private Long linkId;

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
        DepotItem other = (DepotItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHeaderId() == null ? other.getHeaderId() == null : this.getHeaderId().equals(other.getHeaderId()))
            && (this.getMaterialId() == null ? other.getMaterialId() == null : this.getMaterialId().equals(other.getMaterialId()))
            && (this.getMaterialExtendId() == null ? other.getMaterialExtendId() == null : this.getMaterialExtendId().equals(other.getMaterialExtendId()))
            && (this.getMaterialUnit() == null ? other.getMaterialUnit() == null : this.getMaterialUnit().equals(other.getMaterialUnit()))
            && (this.getSku() == null ? other.getSku() == null : this.getSku().equals(other.getSku()))
            && (this.getOperNumber() == null ? other.getOperNumber() == null : this.getOperNumber().equals(other.getOperNumber()))
            && (this.getBasicNumber() == null ? other.getBasicNumber() == null : this.getBasicNumber().equals(other.getBasicNumber()))
            && (this.getUnitPrice() == null ? other.getUnitPrice() == null : this.getUnitPrice().equals(other.getUnitPrice()))
            && (this.getPurchaseUnitPrice() == null ? other.getPurchaseUnitPrice() == null : this.getPurchaseUnitPrice().equals(other.getPurchaseUnitPrice()))
            && (this.getTaxUnitPrice() == null ? other.getTaxUnitPrice() == null : this.getTaxUnitPrice().equals(other.getTaxUnitPrice()))
            && (this.getAllPrice() == null ? other.getAllPrice() == null : this.getAllPrice().equals(other.getAllPrice()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDepotId() == null ? other.getDepotId() == null : this.getDepotId().equals(other.getDepotId()))
            && (this.getAnotherDepotId() == null ? other.getAnotherDepotId() == null : this.getAnotherDepotId().equals(other.getAnotherDepotId()))
            && (this.getTaxRate() == null ? other.getTaxRate() == null : this.getTaxRate().equals(other.getTaxRate()))
            && (this.getTaxMoney() == null ? other.getTaxMoney() == null : this.getTaxMoney().equals(other.getTaxMoney()))
            && (this.getTaxLastMoney() == null ? other.getTaxLastMoney() == null : this.getTaxLastMoney().equals(other.getTaxLastMoney()))
            && (this.getMaterialType() == null ? other.getMaterialType() == null : this.getMaterialType().equals(other.getMaterialType()))
            && (this.getSnList() == null ? other.getSnList() == null : this.getSnList().equals(other.getSnList()))
            && (this.getBatchNumber() == null ? other.getBatchNumber() == null : this.getBatchNumber().equals(other.getBatchNumber()))
            && (this.getExpirationDate() == null ? other.getExpirationDate() == null : this.getExpirationDate().equals(other.getExpirationDate()))
            && (this.getLinkId() == null ? other.getLinkId() == null : this.getLinkId().equals(other.getLinkId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHeaderId() == null) ? 0 : getHeaderId().hashCode());
        result = prime * result + ((getMaterialId() == null) ? 0 : getMaterialId().hashCode());
        result = prime * result + ((getMaterialExtendId() == null) ? 0 : getMaterialExtendId().hashCode());
        result = prime * result + ((getMaterialUnit() == null) ? 0 : getMaterialUnit().hashCode());
        result = prime * result + ((getSku() == null) ? 0 : getSku().hashCode());
        result = prime * result + ((getOperNumber() == null) ? 0 : getOperNumber().hashCode());
        result = prime * result + ((getBasicNumber() == null) ? 0 : getBasicNumber().hashCode());
        result = prime * result + ((getUnitPrice() == null) ? 0 : getUnitPrice().hashCode());
        result = prime * result + ((getPurchaseUnitPrice() == null) ? 0 : getPurchaseUnitPrice().hashCode());
        result = prime * result + ((getTaxUnitPrice() == null) ? 0 : getTaxUnitPrice().hashCode());
        result = prime * result + ((getAllPrice() == null) ? 0 : getAllPrice().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDepotId() == null) ? 0 : getDepotId().hashCode());
        result = prime * result + ((getAnotherDepotId() == null) ? 0 : getAnotherDepotId().hashCode());
        result = prime * result + ((getTaxRate() == null) ? 0 : getTaxRate().hashCode());
        result = prime * result + ((getTaxMoney() == null) ? 0 : getTaxMoney().hashCode());
        result = prime * result + ((getTaxLastMoney() == null) ? 0 : getTaxLastMoney().hashCode());
        result = prime * result + ((getMaterialType() == null) ? 0 : getMaterialType().hashCode());
        result = prime * result + ((getSnList() == null) ? 0 : getSnList().hashCode());
        result = prime * result + ((getBatchNumber() == null) ? 0 : getBatchNumber().hashCode());
        result = prime * result + ((getExpirationDate() == null) ? 0 : getExpirationDate().hashCode());
        result = prime * result + ((getLinkId() == null) ? 0 : getLinkId().hashCode());
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
        sb.append(", materialId=").append(materialId);
        sb.append(", materialExtendId=").append(materialExtendId);
        sb.append(", materialUnit=").append(materialUnit);
        sb.append(", sku=").append(sku);
        sb.append(", operNumber=").append(operNumber);
        sb.append(", basicNumber=").append(basicNumber);
        sb.append(", unitPrice=").append(unitPrice);
        sb.append(", purchaseUnitPrice=").append(purchaseUnitPrice);
        sb.append(", taxUnitPrice=").append(taxUnitPrice);
        sb.append(", allPrice=").append(allPrice);
        sb.append(", remark=").append(remark);
        sb.append(", depotId=").append(depotId);
        sb.append(", anotherDepotId=").append(anotherDepotId);
        sb.append(", taxRate=").append(taxRate);
        sb.append(", taxMoney=").append(taxMoney);
        sb.append(", taxLastMoney=").append(taxLastMoney);
        sb.append(", materialType=").append(materialType);
        sb.append(", snList=").append(snList);
        sb.append(", batchNumber=").append(batchNumber);
        sb.append(", expirationDate=").append(expirationDate);
        sb.append(", linkId=").append(linkId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}