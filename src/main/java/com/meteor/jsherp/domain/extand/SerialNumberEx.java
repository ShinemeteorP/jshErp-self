package com.meteor.jsherp.domain.extand;

import com.meteor.jsherp.domain.SerialNumber;
import lombok.Data;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Data
public class SerialNumberEx extends SerialNumber {
    /**
     * 商品条码
     * */
    private String materialCode;
    /**
     * 商品名称
     * */
    private String materialName;
    /**
     * 创建者名称
     * */
    private String creatorName;
    /**
     * 更新者名称
     * */
    private String updaterName;
    /**单据编号*/
    private String depotHeadNumber;
    /**单据类型（出库入库）*/
    private String depotHeadType;

    private String depotName;

    private String createTimeStr;

    private String updateTimeStr;
}
