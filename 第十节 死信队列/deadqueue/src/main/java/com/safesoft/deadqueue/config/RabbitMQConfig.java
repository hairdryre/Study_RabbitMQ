package com.safesoft.deadqueue.config;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author jay.zhou
 * @date 2019/4/25
 * @time 10:27
 */
@Configuration
@Getter
public class RabbitMQConfig {


    @Value("${defineProps.rabbit.direct.dead.exchage}")
    private String deadExchange;

    @Value("${defineProps.rabbit.direct.dead.queue}")
    private String deadQueue;

    @Value("${defineProps.rabbit.direct.dead.routing.key}")
    private String deadRoutingKey;

    @Value("${defineProps.rabbit.direct.produce.exchange}")
    private String produceExchange;

    @Value("defineProps.rabbit.direct.produce.routing.key")
    private String produceRoutingKey;

    @Value("${defineProps.rabbit.direct.real.queue}")
    private String realQueue;


    //第一步：创建死信队列
    @Bean
    public Queue simpleDeadQueue() {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(5);
        /**
         * 死信队列由死信交换机创建
         * 需要指明死信路由键 以及 消息存活时间
         */
        map.put("x-dead-letter-exchange", deadExchange);
        map.put("x-dead-letter-routing-key", deadRoutingKey);
        map.put("x-message-ttl", 10000);
        return new Queue(deadQueue, true, false, false, map);
    }

    //第二步：创建生产端交换机
    @Bean
    public TopicExchange produceExchange() {
        return new TopicExchange(produceExchange, true, false);
    }

    //第三步:创建绑定：死信队列绑定到生产端
    @Bean
    public Binding simpleDeadBinding() {
        return BindingBuilder.bind(simpleDeadQueue()).to(produceExchange()).with(produceRoutingKey);
    }

    //第四步：创建实际的消费队列
    @Bean
    public Queue realQueue() {
        return new Queue(realQueue, true, false, false, null);
    }

    //第五步：创建死信交换机
    @Bean
    public TopicExchange simpleDeadRealExchange(){
        return new TopicExchange(deadExchange, true, false);
    }

    //第六步：将实际队列绑定到死信交换机.路由键使用死信路由键
    @Bean
    public Binding simpleRealDeadBinding(){
        return BindingBuilder.bind(realQueue()).to(simpleDeadRealExchange()).with(deadRoutingKey);
    }


    /**
     * 定义消息转换实例 ，转化成 JSON传输
     *
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter integrationEventMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 配置启用rabbitmq事务
     *
     * @param connectionFactory connectionFactory
     * @return RabbitTransactionManager
     */
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }
}
