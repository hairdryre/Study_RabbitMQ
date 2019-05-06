package com.safesoft.rabbitmq08.rabbimtMq;

import com.rabbitmq.client.Channel;
import com.safesoft.rabbitmq08.entity.LogEntity;
import com.safesoft.rabbitmq08.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author jay.zhou
 * @date 2019/5/5
 * @time 17:10
 */
@Component
@RabbitListener(queues = {"${defineProps.rabbit.direct.queue}"})
public class LogListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogListener.class);

    @Autowired
	private LogService logService;

    @RabbitHandler
    public void receiver(@Payload LogEntity logEntity, @Headers Channel channel, Message message) throws IOException, InterruptedException {
        LOGGER.info("接收到的日志消息:{}", logEntity);
        try {
            // 确认消息已经消费成功
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logService.insertLog(logEntity);
        } catch (IOException e) {
            LOGGER.error("消费处理异常:{} - {}", logEntity, e);
            // 拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
