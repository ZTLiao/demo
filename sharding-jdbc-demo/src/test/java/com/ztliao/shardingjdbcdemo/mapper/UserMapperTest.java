package com.ztliao.shardingjdbcdemo.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ztliao.shardingjdbcdemo.ShardingJdbcDemoApplication;
import com.ztliao.shardingjdbcdemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author: liaozetao
 * @date: 2021/3/12 9:44 PM
 * @description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcDemoApplication.class)
@WebAppConfiguration
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        for(int i = 0; i < 100; i++){
            User user = new User();
            user.setName("test" + i);
            userMapper.insert(user);
        }
    }

    @Test
    public void select(){
        List<User> users = userMapper.selectList(Wrappers.emptyWrapper());
        if(users != null && !users.isEmpty()){
            for (User user : users) {
                log.info(" user : {}", user);
            }
        }
    }
}
