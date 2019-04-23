package com.safesoft.directexchaner.mq03;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jay.zhou
 * @date 2019/4/23
 * @time 14:22
 */
public class Producer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private static final String EXCHANGE_NAME = "local::mq03:exchange:e01";
    private static final String QUEUE_NAME_01 = "local::mq03:queue:q01";
    private static final String QUEUE_NAME_02 = "local::mq03:queue:q02";

    /**
     * 路由键:美女
     */
    private static final String ROUTING_KEY_BEAUTY = "routekey_beauty";
    /**
     * 路由键:股票
     */
    private static final String ROUTING_KEY_STOCK = "routekey_stock";
    /**
     * 路由键:美食
     */
    private static final String ROUTING_KEY_FOOD = "routekey_food";


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

            //声明一个Direct类型的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
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
            //开始绑定
            /**
             * 第一个队列对美女感兴趣
             * 第二个队列对股票和美食感兴趣
             *
             * 第一个参数是queue：要被绑定的队列名
             * 第一个参数是exchange：队列绑定到哪个路由器上
             * 第三个参数是routingKey：队列对这种路由键感兴趣，路由器会把这种routingKey的消息发送给队列
             */
            //队列一对交换机说，关于美女的消息给我
            channel.queueBind(QUEUE_NAME_01, EXCHANGE_NAME, ROUTING_KEY_BEAUTY);
            //队列二对交换机说，关于股票和美食的消息给我
            channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, ROUTING_KEY_STOCK);
            channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, ROUTING_KEY_FOOD);

            //模拟发送消息
            String message01 = "内衣秀明天在XXX开展";
            String message02 = "中国联通股票大跌";
            String message03 = "黄山美食推荐：蝴蝶面";
            //向交换机发送3个消息，分别是关于美女、股票、美食
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_BEAUTY, null, message01.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_STOCK, null, message02.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_FOOD, null, message03.getBytes("UTF-8"));
            LOGGER.info("消息发送成功");

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
