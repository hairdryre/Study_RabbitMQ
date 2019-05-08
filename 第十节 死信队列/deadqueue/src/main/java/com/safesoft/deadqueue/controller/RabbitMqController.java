package com.safesoft.deadqueue.controller;

import com.safesoft.deadqueue.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jay.zhou
 * @date 2019/5/7
 * @time 10:27
 */
@RestController
public class RabbitMqController {

    private static final String PREFIX = "dead/queue/";
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private RabbitMQConfig config;

    @RequestMapping(PREFIX + "test")
    public void test() {
        template.setExchange(config.getProduceExchange());
        template.setRoutingKey(config.getProduceRoutingKey());
        template.convertAndSend("死信队列来辣！");
    }
}
