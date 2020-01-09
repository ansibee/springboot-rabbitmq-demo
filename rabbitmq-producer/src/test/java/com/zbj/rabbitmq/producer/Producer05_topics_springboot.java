package com.zbj.rabbitmq.producer;

import com.zbj.rabbitmq.producer.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：liuanmin
 * @date ：Created in 2020/1/9
 * @description：
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_topics_springboot {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //使用rabbitTemplate发送消息
    @Test
    public void testSendEmail(){
        //参数列表:String exchange, String routingKey, Object object
        //1. 交换机名称
        //2. routingKey
        //3. 消息内容
        String message = "send email message to user";
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email",message);
    }

    @Test
    public void testSendSMS(){
        //参数列表:String exchange, String routingKey, Object object
        //1. 交换机名称
        //2. routingKey
        //3. 消息内容
        String message = "send sms message to user";
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.sms",message);
    }
}
