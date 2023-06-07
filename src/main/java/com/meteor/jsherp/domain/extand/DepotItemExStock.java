package com.meteor.jsherp.domain.extand;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Data
public class DepotItemExStock {
    private BigDecimal inTotal;
    private BigDecimal outTotal;
    private BigDecimal transfInTotal;
    private BigDecimal transfOutTotal;
    private BigDecimal assemInTotal;
    private BigDecimal assemOutTotal;
    private BigDecimal disAssemInTotal;
    private BigDecimal disAssemOutTotal;
}
