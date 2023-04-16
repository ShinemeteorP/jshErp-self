package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.SerialNumber;
import com.meteor.jsherp.mapper.SerialNumberMapper;
import com.meteor.jsherp.service.SerialNumberService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_serial_number(序列号表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class SerialNumberServiceImpl extends ServiceImpl<SerialNumberMapper, SerialNumber>
    implements SerialNumberService{

}




