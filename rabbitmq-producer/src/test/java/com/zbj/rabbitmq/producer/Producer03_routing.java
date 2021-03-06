package com.zbj.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：liuanmin
 * @date ：Created in 2020/1/3
 * @description： 路由模式(具备发布订阅模式的功能)
 */
@Slf4j
public class Producer03_routing {

    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    //交换机
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    //路由key
    private static final String ROUTING_KEY_EMAIL = "inform_email";
    private static final String ROUTING_KEY_SMS = "inform_sms";

    public static void main(String[] args) {
        //通过连接工厂创建新的连接
        //和mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机,一个mq服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            //建立新连接
            connection = connectionFactory.newConnection();
            //创建会话通道,生产者和mq服务所有通信都在channel通道中完成
            channel = connection.createChannel();
            //声明队列,如果队列没有在mq中要创建
            //参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            /**
             * 参数明细
             * 1.queue 队列名称
             * 2.durable 是否持久化：如果持久化，mq重启后队列还在
             * 3.exclusive 是否独占连接: 队列只允许在该连接中访问，如果connection连接关闭队列自动删除，如果将此参数设置true可用于临时队列创建
             * 4.autoDelete 是否自动删除：队列不再使用时是否自动删除此队列，如果将此参数和exclusive设置为true，就可以实现临时队列（队列不使用就自动删除）
             * 5.arguments 可以设置一个队列的扩展参数，比如可设置存活时间
             */
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            //声明一个交换机
            //参数：String exchange, String type
            /**
             * 参数明细：
             * 1.exchange 交换机名称
             * 2.type 交换机类型
             * fanout:对应的rabbitmq的工作模式是 publish/subscribe (发布订阅模式)
             * direct:对应的routing工作模式
             * topic:对应通配符工作模式
             * headers:对应headers工作模式
             */
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            //进行交换机和队列绑定
            //参数：String queue, String exchange, String routingKey
            /**
             * 参数明细：
             * 1.queue 队列名称
             * 2.exchange 交换机名称
             * 3.routingKey 路由key,作用是交换机根据路由key的值将消息转发到指定的队列中，在发布订阅模式中设置为空字符串
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_ROUTING_INFORM,ROUTING_KEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_ROUTING_INFORM,"liuanmin");
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,ROUTING_KEY_SMS);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,"liuanmin");
            //发送消息
            //参数：String exchange, String routingKey, BasicProperties props, byte[] body
            /**
             * 参数明细
             * 1.exchange 交换机，如果不指定将使用mq的默认交换机(设置为"")
             * 2.routingKey 路由key，交换机根据路由key将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
             * 3.props 消息属性
             * 4.body 消息内容
             */
            /*for (int i = 0; i < 5; i++) {
                //消息内容
                //发送消息指定 routingKey
                String message = "send email inform message to user";
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTING_KEY_EMAIL,null,message.getBytes());
                log.info("send to mq: {}",message);
            }
            for (int i = 0; i < 5; i++) {
                //消息内容
                //发送消息指定 routingKey
                String message = "send sms inform message to user";
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS,null,message.getBytes());
                log.info("send to mq: {}",message);
            }*/
            /**
             * 通过指定路由key,完成发布订阅模式的功能
             */
            for (int i = 0; i < 5; i++) {
                //消息内容
                //发送消息指定 routingKey
                String message = "send inform message to user";
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, "liuanmin",null,message.getBytes());
                log.info("send to mq: {}",message);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
