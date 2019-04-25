package com.safesoft.ssm.rabbitMQ;

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
 * @date 2019/4/25
 * @time 11:07
 * 监听队列02的消息，关于美食和股票的消息，此消费者都感兴趣
 */
@Component
@RabbitListener(queues = {"${defineProps.rabbit.direct.queue.queue02}"})
public class FoodListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(FoodListener.class);

    @RabbitHandler
    public void receiver(@Payload String msgAboutFoodOrStock, @Headers Channel channel, Message message) throws IOException {
        LOGGER.info("接收到的消息:{}", msgAboutFoodOrStock);
        try {
            // 确认消息已经消费成功
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            LOGGER.info("确认处理消息:{}", msgAboutFoodOrStock);
        } catch (IOException e) {
            LOGGER.error("消费处理异常:{} - {}", msgAboutFoodOrStock, e);
            // 拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
