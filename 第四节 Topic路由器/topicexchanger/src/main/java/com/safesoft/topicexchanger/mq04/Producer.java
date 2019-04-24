package com.safesoft.topicexchanger.mq04;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jay.zhou
 * @date 2019/4/24
 * @time 10:30
 */
public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private static final String EXCHANGE_NAME = "local::mq04:exchange:e01";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String message = "topic交换机很有用";
            //声明一个TOPIC类型的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            //channel.basicPublish(EXCHANGE_NAME, "like.orange.color", null, message.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, "lazy.boy.girl", null, message.getBytes("UTF-8"));

            LOGGER.info("消息发送成功:{}", message);
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
