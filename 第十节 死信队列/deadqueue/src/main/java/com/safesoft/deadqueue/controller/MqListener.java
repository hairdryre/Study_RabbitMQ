package com.safesoft.deadqueue.controller;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author jay.zhou
 * @date 2019/5/7
 * @time 11:21
 */
@Component
@RabbitListener(queues = "${defineProps.rabbit.direct.real.queue}")
public class MqListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqListener.class);

    @RabbitHandler
    public void handleMessage(@Payload String msg, @Headers Channel channel, Message message) throws IOException {
        try {
            LOGGER.info("接收到消息:{}", msg);
            long tag = message.getMessageProperties().getDeliveryTag();
            //确定消费
            channel.basicAck(tag, false);
        } catch (IOException e) {
            LOGGER.error("消费异常");
            long tag = message.getMessageProperties().getDeliveryTag();
            //第三个参数，是否重新入队列，让别的消费者消费
            //设置为false，那么这个消息就真的被丢弃了
            channel.basicNack(tag,false,true);
        }
    }
}
