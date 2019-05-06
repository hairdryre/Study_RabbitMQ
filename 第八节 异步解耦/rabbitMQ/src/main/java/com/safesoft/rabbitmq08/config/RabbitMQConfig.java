package com.safesoft.rabbitmq08.config;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jay.zhou
 * @date 2019/5/5
 * @time 16:59
 */
@Getter
@Configuration
public class RabbitMQConfig {

    /**
     * 交换机名称
     */
    @Value("${defineProps.rabbit.direct.exchange}")
    private String directExchange;

    /**
     * 队列名称，
     */
    @Value("${defineProps.rabbit.direct.queue}")
    private String queue;

    /**
     * 路由键
     */
    @Value("${defineProps.rabbit.direct.routing.key}")
    private String routingKey;

    /**
     * 定义交换器
     * 三个参数解释如下
     * name:交换机名称
     * durable:是否持久化，true表示交换机会被写入磁盘，即使RabbitMQ服务器宕机，也能恢复此交换机
     * autoDelete:表示消息交换机没有在使用时将被自动删除 默认是false
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange, true, false);
    }

    /**
     * 定义队列01
     * 第一个参数是queue：要创建的队列名
     * 第二个参数是durable：是否持久化。如果为true，可以在RabbitMQ崩溃后恢复队列
     * 第三个参数是exclusive：true表示一个队列只能被一个消费者占有并消费
     * 第四个参数是autoDelete：true表示服务器不在使用这个队列是会自动删除它
     * 第五个参数是arguments：其它参数
     */
    @Bean
    public Queue queue() {
        return new Queue(queue, true, false, false, null);
    }


    /**
     * 绑定-将队列绑定到路由器上，队列告诉路由器它感兴趣的话题（路由键）
     *
     * @param queue          队列
     * @param directExchange 交换器
     * @return Binding
     */
    @Bean
    public Binding queue01Binding(Queue queue, DirectExchange directExchange) {
        //队列一绑定到路由器上，并告诉路由器，关于美女的消息，发送给它
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
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
}
