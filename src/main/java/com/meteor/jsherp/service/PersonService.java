package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.Person;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_person(经手人表)】的数据库操作Service
* @createDate 2023-04-13 18:07:55
*/
public interface PersonService extends IService<Person> ,CommonService<Person> {

    /**
     * 获取该租户下所有Person的id和name，
     * @return  id为key，name为value的map
     */
    Map<Long, String> getPersonIdAndName();
}
