package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.DepotItem;
import com.meteor.jsherp.domain.SerialNumber;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.extand.SerialNumberEx;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_serial_number(序列号表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface SerialNumberService extends IService<SerialNumber> {

    /**
     * 录入序列号
     * @param type
     * @param subType
     * @param number
     * @param materialId
     * @param depotId
     * @param snList
     * @param userId 登入用户id
     * @return
     */
    int addSerialNumberByBill(String type, String subType, String number, Long materialId, Long depotId, String snList, Long userId);

    /**
     * 出售更新序列号
     * @param depotItem
     * @param number
     * @param user
     * @param toNull
     */
    int checkAndUpdateSerialNumber(DepotItem depotItem, String number, User user, String toNull);

    /**
     * 根据参数获取可用的序列号列表
     * @param name
     * @param number
     * @param depotId
     * @param barCode
     * @param i
     * @param rows
     * @return
     */
    List<SerialNumberEx> getSerialNumberListByParam(String name, String number, Long depotId, String barCode, int i, Integer rows);

    /**
     * 根据参数获取可用的序列号数量
     * @param name
     * @param number
     * @param depotId
     * @param barCode
     * @return
     */
    Long getSerialNumberCountByParam(String name, String number, Long depotId, String barCode);

    /**
     * 根据number列删除序列号
     * @param number
     */
    int deleteByInBillNo(String number);

    /**
     * 根据outBillNo和materialId修改序列号的is_sell状态和out_bill_no
     * @param materialId
     * @param outBillNo
     * @param count
     * @param user
     * @return
     */
    int updateByOutBillNoAndMId(Long materialId, String outBillNo, BigDecimal count, User user);
}
