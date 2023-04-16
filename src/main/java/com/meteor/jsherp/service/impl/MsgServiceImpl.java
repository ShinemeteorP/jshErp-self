package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Msg;
import com.meteor.jsherp.mapper.MsgMapper;
import com.meteor.jsherp.service.MsgService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_msg(消息表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class MsgServiceImpl extends CommonServiceImpl<MsgMapper, Msg>
    implements MsgService{

}




