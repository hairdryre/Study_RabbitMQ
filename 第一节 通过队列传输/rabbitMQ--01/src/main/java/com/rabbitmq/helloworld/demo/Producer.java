package com.rabbitmq.helloworld.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jay.zhou
 * @date 2019/4/22
 * @time 16:46
 * 生产者
 */
public class Producer {
    private static final String QUEUE_NAME = "local::queue:01";
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        try {
            //连接管理器：我们的应用程序与RabbitMQ建立连接的管理器。
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ服务器地址
            factory.setHost("127.0.0.1");
            //设置帐号密码,默认为guest/guest，所以下面两行可以省略
            factory.setUsername("guest");
            factory.setPassword("guest");

            //新建一个连接
            Connection connection = factory.newConnection();
            //再创建一个信道，这个就相当于一个邮局
            //它是消息推送使用的通道。
            Channel channel = connection.createChannel();

            //首先在通道中申明一个队列
            /**
             * 第一个参数是queue：要创建的队列名
             * 第二个参数是durable：是否持久化。如果为true，可以在RabbitMQ崩溃后恢复消息
             * 第三个参数是exclusive：true表示一个队列只能被一个消费者占有并消费
             * 第四个参数是autoDelete：true表示服务器不在使用这个队列是会自动删除它
             * 第五个参数是arguments：其它参数
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //创建一条消息
            String message = "我养你啊！";
            //采用二进制流的方式传输
            byte[] msg = message.getBytes("UTF-8");
            //channel是一个邮局，它接收到msg数据，并将纳入到QUEUE_NAME队列中
            channel.basicPublish("", QUEUE_NAME, null, msg);

            LOGGER.info("发送端发送消息:{}",message);
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
