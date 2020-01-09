package com.zbj.rabbitmq.consumer.mq;

import com.rabbitmq.client.Channel;
import com.zbj.rabbitmq.consumer.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ：liuanmin
 * @date ：Created in 2020/1/9
 * @description：
 */
@Component
public class ReceiveHandler {

    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void send_email(String msg, Message message, Channel channel){
        System.out.println("receive message is "+msg);
    }

    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
    public void send_sms(String msg, Message message, Channel channel){
        System.out.println("receive message is "+msg);
    }
}
