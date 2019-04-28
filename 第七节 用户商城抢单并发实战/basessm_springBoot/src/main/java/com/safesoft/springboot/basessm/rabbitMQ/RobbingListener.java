package com.safesoft.springboot.basessm.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.safesoft.springboot.basessm.service.ProductService;
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
 * @date 2019/4/25
 * @time 11:07
 */
@Component
@RabbitListener(queues = {"${defineProps.rabbit.direct.queue}"})
public class RobbingListener {

    @Autowired
	private ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RobbingListener.class);

    @RabbitHandler
    public void receiver(@Payload Integer userId, @Headers Channel channel, Message message) throws IOException {
        LOGGER.info("用户{}开始抢单", userId);
        try {
            //处理消息
            productService.robbingProduct(userId);
            // 确认消息已经消费成功
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            LOGGER.error("消费处理异常:{} - {}", userId, e);
            // 拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}