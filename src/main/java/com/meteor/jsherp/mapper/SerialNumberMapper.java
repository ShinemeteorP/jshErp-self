package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.SerialNumber;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meteor.jsherp.domain.extand.SerialNumberEx;

import java.util.Date;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_serial_number(序列号表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:55
* @Entity generator.domain.SerialNumber
*/
public interface SerialNumberMapper extends BaseMapper<SerialNumber> {

    int checkAndUpdateSerialNumber(Long materialId, String number, Long id, Date date, String[] numberList);

    List<SerialNumberEx> getSerialNumberListByParam(String name, String number, Long depotId, String barCode, int offset, Integer rows);

    Long getSerialNumberCountByParam(String name, String number, Long depotId, String barCode);

    int updateByOutBillNoAndMId(Long materialId, String outBillNo, int count, Long id);
}




