package com.aries.phoenix;

import com.aries.phoenix.enums.UserSexEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
public class UserTest {
//    @Resource
//    private UserMapper mapper;
//
//    @Test
//    public void add() {
//        User user = new User();
//        user.setUserName("jin");
//        user.setPassWord("123");
//        user.setNickName("long");
//        user.setUserSex(UserSexEnum.MAN);
//        mapper.insert(user);
//    }
}
