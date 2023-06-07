package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.constant.BusinessConstant;
import com.meteor.jsherp.domain.Sequence;
import com.meteor.jsherp.mapper.SequenceMapper;
import com.meteor.jsherp.service.SequenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author 刘鑫
* @description 针对表【jsh_sequence(单据编号表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class SequenceServiceImpl extends ServiceImpl<SequenceMapper, Sequence>
    implements SequenceService{

    @Resource
    private SequenceMapper sequenceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String buildNumber() {
        sequenceMapper.updateCurrentVal(BusinessConstant.DEPOT_NUMBER_SEQ);
        Sequence sequence = sequenceMapper.selectOne(new QueryWrapper<Sequence>()
                .eq("seq_name", BusinessConstant.DEPOT_NUMBER_SEQ));
        Long currentVal = sequence.getCurrentVal();
        if(currentVal < BusinessConstant.SEQ_TO_STRING_MIN_LENGTH){
            int length = BusinessConstant.SEQ_TO_STRING_MIN_LENGTH.toString().length() - currentVal.toString().length();
            StringBuilder numStr = new StringBuilder(currentVal.toString());
            for (int i = 0; i < length; i++) {
                numStr.insert(0, "0");
            }
            return numStr.toString();
        }
        return currentVal.toString();
    }
}




