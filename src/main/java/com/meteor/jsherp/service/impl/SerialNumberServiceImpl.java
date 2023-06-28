package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.constant.ExceptionConstant;
import com.meteor.jsherp.domain.DepotItem;
import com.meteor.jsherp.domain.SerialNumber;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.domain.extand.SerialNumberEx;
import com.meteor.jsherp.exception.BusinessException;
import com.meteor.jsherp.mapper.SerialNumberMapper;
import com.meteor.jsherp.service.SerialNumberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author 刘鑫
* @description 针对表【jsh_serial_number(序列号表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class SerialNumberServiceImpl extends ServiceImpl<SerialNumberMapper, SerialNumber>
    implements SerialNumberService{

    @Resource
    private SerialNumberMapper serialNumberMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addSerialNumberByBill(String type, String subType, String number, Long materialId, Long depotId, String snList, Long userId) {
        //录入序列号的时候不能重复
        if("入库".equals(type) && (BusinessConstant.SUB_TYPE_SALES.equals(subType)
                || BusinessConstant.SUB_TYPE_OTHER.equals(subType) || BusinessConstant.SUB_TYPE_SALES_RETURN.equals(subType)
                || BusinessConstant.SUB_TYPE_RETAIL_RETURN.equals(subType))){
            String[] snArr = snList.replaceAll("，", ",").split(",");
            for (int i = 0; i < snArr.length; i++) {
                String sn = snArr[i];
                //根据serialNumber和is_sell标识，查询已有的serialNumber
                QueryWrapper<SerialNumber> queryWrapper = new QueryWrapper<SerialNumber>().eq("serial_number", sn.trim()).eq("is_sell", "0");
                List<SerialNumber> serialNumberList = serialNumberMapper.selectList(queryWrapper);
                //不存在则新增
                if(serialNumberList == null || serialNumberList.size() == 0){
                    SerialNumber serialNumber = new SerialNumber();
                    serialNumber.setMaterialId(materialId);
                    serialNumber.setDepotId(depotId);
                    serialNumber.setSerialNumber(sn.trim());
                    serialNumber.setCreateTime(new Date());
                    serialNumber.setUpdateTime(new Date());
                    serialNumber.setInBillNo(number);
                    serialNumber.setCreator(userId);
                    serialNumber.setUpdater(userId);
                    return serialNumberMapper.insert(serialNumber);
                }else{
                    if(!number.equals(serialNumberList.get(0).getInBillNo())){
                        throw new BusinessException(ExceptionConstant.SERIAL_NUMBER_ALREADY_EXISTS_CODE, ExceptionConstant.SERIAL_NUMBER_ALREADY_EXISTS_MSG);
                    }
                }
            }
        }

        return 0;
    }

    @Override
    public int checkAndUpdateSerialNumber(DepotItem depotItem, String number, User user, String snList) {
        if(depotItem != null){
            String[] numberList = snList.replaceAll("，", ",").split(",");
            return serialNumberMapper.checkAndUpdateSerialNumber(depotItem.getMaterialId(), number, user.getId(), new Date(), numberList);
        }
        return 0;
    }

    @Override
    public List<SerialNumberEx> getSerialNumberListByParam(String name, String number, Long depotId, String barCode, int offset, Integer rows) {

        return serialNumberMapper.getSerialNumberListByParam(name, number, depotId, barCode, offset, rows);
    }

    @Override
    public Long getSerialNumberCountByParam(String name, String number, Long depotId, String barCode) {

        return serialNumberMapper.getSerialNumberCountByParam(name, number, depotId, barCode);
    }

    @Override
    public int deleteByInBillNo(String number) {
        return serialNumberMapper.delete(new QueryWrapper<SerialNumber>().eq("in_bill_no", number));
    }

    @Override
    public int updateByOutBillNoAndMId(Long materialId, String outBillNo, BigDecimal count, User user) {
        int countInt = count == null ? 0 : count.intValue();
        return serialNumberMapper.updateByOutBillNoAndMId(materialId, outBillNo, countInt, user.getId());
    }
}




