package com.rabbitmq.helloworld.demo;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author jay.zhou
 * @date 2019/4/22
 * @time 16:53
 */
public class MsgConsumer {
    private static final String QUEUE_NAME = "local::queue:01";
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgConsumer.class);

    public static void main(String[] args) {
        try {
            //连接管理器：我们的应用程序与RabbitMQ建立连接的管理器。
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ服务器地址
            factory.setHost("127.0.0.1");

            //新建一个连接
            Connection connection = factory.newConnection();
            //再创建一个信道，这个就相当于一个邮局
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

            //创建消费者，并重写如何消费的方法，我们这里仅仅就是打印一下消息啦
            //首先从信道里面获取数据
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    LOGGER.info("收件人收到消息:{}", message);
                }
            };

            //收到了消息后，提示邮局，我已经收到消息了。可以给我发送其它消息
            //第二个参数autoAck如果为false，那么消息会一直保存在RabbitMQ服务器
            //消费者没有确认消息被消费，消息一直留在队列中，只有当从有新的消费者加入时，消息被分发到新的消费者。
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
