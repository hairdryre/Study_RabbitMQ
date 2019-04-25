package com.safesoft.ssm.controller;

import com.safesoft.ssm.entity.UserEntity;
import com.safesoft.ssm.rabbitMQ.RabbitMQSender;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author jay.zhou
 * @date 2019/4/25
 * @time 10:53
 */
@RestController
public class RabbitMQController {

    private static final Logger LOGGER = getLogger(RabbitMQController.class);

    @Autowired
    private RabbitMQSender sender;


    @GetMapping(value = "/sendToQueue01")
    public void testSend(@RequestBody UserEntity userEntity) {
        sender.sendMessageToQueue01AboutBeauty(userEntity);
    }

    @GetMapping(value = "/sendToQueue02AboutStock")
    public void testSend(String message) {
        sender.sendMessageToQueue02AboutStock(message);
    }

    @GetMapping(value = "/sendToQueue02AboutFood")
    public void testSend2(String message) {
        sender.sendMessageToQueue02AboutFood(message);
    }
}
