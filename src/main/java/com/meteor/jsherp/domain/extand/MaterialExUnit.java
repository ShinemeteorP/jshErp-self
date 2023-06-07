package com.meteor.jsherp.domain.extand;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meteor.jsherp.domain.DepotItem;
import com.meteor.jsherp.domain.Material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialExUnit extends Material {
    private String unitName;

    private String categoryName;

    private String materialOther;

    private BigDecimal stock;

    private BigDecimal purchaseDecimal;

    private BigDecimal commodityDecimal;

    private BigDecimal wholesaleDecimal;

    private BigDecimal lowDecimal;

    private BigDecimal billPrice;

    @JsonProperty("mBarCode")
    @JSONField(name = "mBarCode")
    private String mBarCode;

    private String commodityUnit;

    private Long meId;

    private BigDecimal initialStock;

    private BigDecimal currentStock;

    private BigDecimal currentStockPrice;

    private String sku;

    private Long depotId;

    /**
     * 换算为大单位的库存
     */
    private String bigUnitStock;

    public MaterialExUnit(DepotItem depotItem){
        this.sku = depotItem.getSku();
        this.setId(depotItem.getMaterialId());
        this.meId = depotItem.getMaterialExtendId();
    }
    public MaterialExUnit(String sku, Long materialId, Long materialExtendId, Long unitId, String commodityUnit){
        this.sku = sku;
        this.meId = materialExtendId;
        this.setId( materialId);
        this.setUnitId(unitId);
        this.commodityUnit = commodityUnit;
    }
}
