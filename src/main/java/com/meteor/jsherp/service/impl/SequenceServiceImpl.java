package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Sequence;
import com.meteor.jsherp.mapper.SequenceMapper;
import com.meteor.jsherp.service.SequenceService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_sequence(单据编号表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class SequenceServiceImpl extends ServiceImpl<SequenceMapper, Sequence>
    implements SequenceService{

}




