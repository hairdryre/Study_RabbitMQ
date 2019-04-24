package com.safesoft.topicexchanger.mq04;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author jay.zhou
 * @date 2019/4/24
 * @time 10:40
 */
public class Consumer01 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private static final String EXCHANGE_NAME = "local::mq04:exchange:e01";
    private static final String QUEUE_NAME_01 = "local::mq04:queue:q01";
    private static final String QUEUE_NAME_02 = "local::mq04:queue:q02";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            //声明一个Direct类型的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            //申明两个个队列
            /**
             * 第一个参数是queue：要创建的队列名
             * 第二个参数是durable：是否持久化。如果为true，可以在RabbitMQ崩溃后恢复消息
             * 第三个参数是exclusive：true表示一个队列只能被一个消费者占有并消费
             * 第四个参数是autoDelete：true表示服务器不在使用这个队列是会自动删除它
             * 第五个参数是arguments：其它参数
             */
            channel.queueDeclare(QUEUE_NAME_01, true, false, false, null);
            channel.queueDeclare(QUEUE_NAME_02, true, false, false, null);

            final String ROUTING_KEY_ORANGE = "*.orange.*";
            final String ROUTING_KEY_LAZY = "lazy.#";
            //队列一对ORANGE感兴趣，匹配  XXX.orange.XXX 的消息
            channel.queueBind(QUEUE_NAME_01, EXCHANGE_NAME, ROUTING_KEY_ORANGE);
            //队列二对LAZY感兴趣，匹配  lazy.XXX.XXX.XXX
            channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, ROUTING_KEY_LAZY);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    final String message = new String(body, "UTF-8");
                    LOGGER.info("队列一收到消息:{}", message);
                }
            };

            //队列一确认消息
            channel.basicConsume(QUEUE_NAME_01, true, consumer);

        } catch (Exception e) {
            LOGGER.error("an exception was occurred , caused by :{}", e.getMessage());
        }
    }
}
