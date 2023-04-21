package com.meteor.jsherp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.Account;
import com.meteor.jsherp.domain.Person;
import com.meteor.jsherp.mapper.PersonMapper;
import com.meteor.jsherp.service.PersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_person(经手人表)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:55
*/
@Service
public class PersonServiceImpl extends CommonServiceImpl<PersonMapper, Person>
    implements PersonService{

    @Resource
    private PersonMapper personMapper;

    @Override
    public Map<Long, String> getPersonIdAndName() {
        List<Person> personList = personMapper.selectList(null);
        Map<Long, String> res = new HashMap<>();
        for (Person a :
                personList) {
            res.put(a.getId(), a.getName());
        }
        return res;
    }
}




