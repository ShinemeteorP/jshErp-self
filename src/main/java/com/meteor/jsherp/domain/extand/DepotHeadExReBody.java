package com.meteor.jsherp.domain.extand;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 刘鑫
 * @version 1.0
 * @description 用来接收新增表单记录的信息，info记录着表单的所有数据
 */
@Data
public class DepotHeadExReBody {
    private Long id;

    private String info;

    private String rows;

    private BigDecimal preTotalPrice;
}
