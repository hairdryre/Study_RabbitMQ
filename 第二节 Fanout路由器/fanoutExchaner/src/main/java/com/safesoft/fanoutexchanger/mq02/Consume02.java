package com.safesoft.fanoutexchanger.mq02;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;

/**
 * @author jay.zhou
 * @date 2019/4/23
 * @time 10:49
 */
public class Consume02 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private static final String EXCHANGE_NAME = "local::mq02:exchange:e01";
    private static final String QUEUE_NAME_01 = "local::mq02:queue:q01";
    private static final String QUEUE_NAME_02 = "local::mq02:queue:q02";

    public static void main(String[] args) {
        try {
            //设置RabbitMQ服务器信息
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            //申明一个队列
            /**
             * 第一个参数是queue：要创建的队列名
             * 第二个参数是durable：是否持久化。如果为true，可以在RabbitMQ崩溃后恢复消息
             * 第三个参数是exclusive：true表示一个队列只能被一个消费者占有并消费
             * 第四个参数是autoDelete：true表示服务器不在使用这个队列是会自动删除它
             * 第五个参数是arguments：其它参数
             */
            channel.queueDeclare(QUEUE_NAME_02, true, false, false, null);
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);

            //在fanout类型的路由器中，路由键无效，所以设计为空字符串
            final String routeKey = "";
            //将这个队列订阅到这个路由器上，表示这个队列对这个路由器感兴趣
            channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, routeKey);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    LOGGER.info("收件人二号收到消息:{}", message);
                }
            };
            //队列一确认收到消息
            channel.basicConsume(QUEUE_NAME_02, true, consumer);

        } catch (Exception e) {
            LOGGER.error("an exception was occurred , caused by :{}", e.getMessage());
        }

    }
}
