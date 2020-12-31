package com.example.user;

import com.example.user.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class whatever {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        System.out.println(userMapper.selectList(null));
    }
}
