package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Person;
import com.meteor.jsherp.mapper.PersonMapper;
import com.meteor.jsherp.service.PersonService;
import org.springframework.stereotype.Service;

/**
* @author 刘鑫
* @description 针对表【jsh_person(经手人表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person>
    implements PersonService{

}




