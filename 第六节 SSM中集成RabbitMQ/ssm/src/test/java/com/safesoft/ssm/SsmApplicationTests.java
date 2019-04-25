package com.safesoft.ssm;

import com.safesoft.ssm.entity.UserEntity;
import com.safesoft.ssm.rabbitconfig.RabbitMQConfig;
import com.safesoft.ssm.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SsmApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitMQConfig config;

    @Test
    public void notNull() {
        Assert.assertNotNull(userService);
    }

    @Test
    public void testMethod() {
        Assert.assertEquals("jay", userService.selectByPrimaryKey(1L).getUsername());
        //Assert.assertTrue(userService.insert(new UserEntity("KK", 23)));
    }

    @Test
    public void testMethod2() {
        Assert.assertEquals(config.getBeautyRoutingKey(),"mq06::routeKey_love_beauty");
    }

}
