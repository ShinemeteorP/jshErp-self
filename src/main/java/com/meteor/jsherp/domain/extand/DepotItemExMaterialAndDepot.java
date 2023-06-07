package com.meteor.jsherp.domain.extand;

import com.meteor.jsherp.domain.DepotItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Data
public class DepotItemExMaterialAndDepot extends DepotItem {
    private Long MId;

    private String MName;

    private String MModel;

    private String MaterialUnit;

    private String MColor;

    private String MStandard;

    private String MMfrs;

    private String MOtherField1;

    private String MOtherField2;

    private String MOtherField3;

    private String enableSerialNumber;

    private String enableBatchNumber;

    private String DepotName;

    private String AnotherDepotName;

    private Long UnitId;

    private String unitName;

    private Integer ratio;

    private String otherUnit;

    private BigDecimal presetPriceOne;

    private String priceStrategy;

    private BigDecimal purchaseDecimal;

    private String barCode;

    private BigDecimal weight;
}
