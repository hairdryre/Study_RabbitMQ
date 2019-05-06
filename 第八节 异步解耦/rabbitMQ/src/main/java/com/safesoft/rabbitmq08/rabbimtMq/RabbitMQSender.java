package com.safesoft.rabbitmq08.rabbimtMq;

import com.safesoft.rabbitmq08.config.RabbitMQConfig;
import com.safesoft.rabbitmq08.entity.LogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jay.zhou
 * @date 2019/5/5
 * @time 17:07
 */
@Component
public class RabbitMQSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @PostConstruct
    private void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        //与confirm模式互斥
        rabbitTemplate.setChannelTransacted(false);
    }


    /**
     * 异步处理：处理日志
     *
     * @param logEntity 日志实体数据
     * @return 成功
     */
    public Boolean handleSysLog(LogEntity logEntity) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.getDirectExchange(), rabbitMQConfig.getRoutingKey(), logEntity);
        return Boolean.TRUE;
    }


    /**
     * 消息发送到交换器Exchange后触发回调。
     * 使用该功能需要开启确认，spring-boot中配置如下：
     * spring.rabbitmq.publisher-confirms = true
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            LOGGER.info("消息已确认 cause:{} - {}", cause, correlationData);
        } else {
            LOGGER.error("消息未确认 cause:{} - {}", cause, correlationData);
        }
    }

    /**
     * 通过实现ReturnCallback接口，
     * 如果消息从交换器发送到对应队列失败时触发
     * 比如根据发送消息时指定的routingKey找不到队列时会触发
     * 使用该功能需要开启确认，spring-boot中配置如下：
     * spring.rabbitmq.publisher-returns = true
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        LOGGER.error("消息被退回:{}", message);
        LOGGER.error("消息使用的交换机:{}", exchange);
        LOGGER.error("消息使用的路由键:{}", routingKey);
        LOGGER.error("描述:{}", replyText);
    }

}
