package com.zqz.service.biz;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zqz.service.utils.MqUtil;
import org.springframework.stereotype.Service;


/**
 * @Author zy
 * @Date 2022/5/25
 */
@Service
public class MqProducer {

    private final static String QUEUE_NAME = "ZQZ_TEST_QUEUE";
    private final static String EXCHANGE = "ZQZ_TEST2_EXCHANGE";
    private final static String EXCHANGE_ROUTING_KEY = "TEST_KEY";


    public void sendMsg(String message) {
        Connection connection = null;
        Channel channel = null;
        try {
            // 1、获取到连接
            connection = MqUtil.getConnection();
            // 2、从连接中创建通道，使用通道才能完成消息相关的操作
            channel = connection.createChannel();
            // 3、声明（创建）队列
            //参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            /**
             * 参数明细
             * 1、queue 队列名称
             * 2、durable 是否持久化，如果持久化，mq重启后队列还在
             * 3、exclusive 是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除,如果将此参数设置true可用于临时队列的创建
             * 4、autoDelete 自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
             * 5、arguments 参数，可以设置一个队列的扩展参数，比如：可设置存活时间
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 4、消息内容
            // 向指定的队列中发送消息
            //参数：String exchange, String routingKey, BasicProperties props, byte[] body
            /**
             * 参数明细：
             * 1、exchange，交换机，如果不指定将使用mq的默认交换机（设置为""）
             * 2、routingKey，路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
             * 3、props，消息的属性
             * 4、body，消息内容
             */
            channel.basicPublish(EXCHANGE, EXCHANGE_ROUTING_KEY, null, message.getBytes());
            System.out.println("发送消息: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != channel) {
                    channel.close();
                }
                if (null != connection) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}