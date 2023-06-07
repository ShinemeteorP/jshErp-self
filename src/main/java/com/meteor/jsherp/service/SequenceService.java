package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Sequence;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 刘鑫
* @description 针对表【jsh_sequence(单据编号表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface SequenceService extends IService<Sequence> {

    /**
     * 更新目前的单据编号，并获取最新单据编号
     * @return
     */
    String buildNumber();
}
