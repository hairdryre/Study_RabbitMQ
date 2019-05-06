package com.safesoft.rabbitmq08.controller;

import com.safesoft.rabbitmq08.entity.LogEntity;
import com.safesoft.rabbitmq08.entity.UserEntity;
import com.safesoft.rabbitmq08.rabbimtMq.RabbitMQSender;
import com.safesoft.rabbitmq08.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jay.zhou
 * @date 2019/5/5
 * @time 17:13
 */
@RestController
public class BusinessController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private LogService logService;

    private static final String PREFIX = "/rabbitmq/";

    @RequestMapping(value = PREFIX + "updateUserEntity")
    public String updateUserEntity(@RequestBody UserEntity userEntity) throws InterruptedException {
        //开始时间
        Long startTime = System.currentTimeMillis();
        LOGGER.info("接收到用户{}", userEntity);
        //模拟更新业务花费1秒
        Thread.sleep(1000);

        //构建日志
        LogEntity logEntity = new LogEntity("updateUserEntity", "用户模块");
        //异步操作系统，使用中间件提高性能
        rabbitMQSender.handleSysLog(logEntity);
        LOGGER.info("主线程继续执行其它业务");
        LOGGER.info("异步执行共花费{}秒", System.currentTimeMillis() - startTime);
//        //同步操作日志，程序顺序执行，主要性能耗费在日志上
//        logService.insertLog(logEntity);
//        LOGGER.info("同步执行共花费{}豪秒", System.currentTimeMillis() - startTime);
        return "success";
    }
}
