package com.safesoft.fanoutexchanger.mq02;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;

/**
 * @author jay.zhou
 * @date 2019/4/23
 * @time 10:10
 */
public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private static final String EXCHANGE_NAME = "local::mq02:exchange:e01";

    public static void main(String[] args) {
        try {
            //连接管理器：我们的应用程序与RabbitMQ建立连接的管理器。
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ服务器地址
            factory.setHost("127.0.0.1");
            //设置帐号密码,默认为guest/guest，所以下面两行可以省略
            factory.setUsername("guest");
            factory.setPassword("guest");

            //创建一个连接
            Connection connection = factory.newConnection();
            //创建一个信道
            Channel channel = connection.createChannel();
            //通过信道创建一个交换机
            /**
             * 第一个参数是交换机的名称
             * 第二个参数是交换机的类型 Fanout：
             * fanout：直接把队列绑定到路由器。路由器在收到消息后，直接把消息投递到队列中，不需要路由键。
             */
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);

            //消息用二进制的方式传输
            final String message = "中国联通股票价格又跌了，不开心";
            final byte[] msg = message.getBytes("UTF-8");

            //直接把消息发送到路由器。路由在收到消息后，直接投递到订阅这个路由器的队列
            channel.basicPublish(EXCHANGE_NAME, "", null, msg);

            LOGGER.info("消息发送成功:{}", message);
            channel.close();
            connection.close();
        } catch (Exception e) {
            LOGGER.error("an exception was occurred , caused by :{}", e.getMessage());
        }

    }

}
