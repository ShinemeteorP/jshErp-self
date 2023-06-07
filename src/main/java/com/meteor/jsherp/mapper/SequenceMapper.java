package com.meteor.jsherp.mapper;

import com.meteor.jsherp.domain.Sequence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 刘鑫
* @description 针对表【jsh_sequence(单据编号表)】的数据库操作Mapper
* @createDate 2023-04-13 18:07:55
* @Entity generator.domain.Sequence
*/
public interface SequenceMapper extends BaseMapper<Sequence> {

    void updateCurrentVal(@Param("seqName") String name);
}




