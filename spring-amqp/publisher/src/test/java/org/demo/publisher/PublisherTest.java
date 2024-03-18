package org.demo.publisher;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final static String QUEUE_NAME = "hello";
    private final static String WORK_QUEUE_NAME = "work.hello";
    private final static String EXCHANGE_FANOUT = "exchange.fanout";

    /**
     * spring-amqp 向Basic Queue 简单队列发送消息
     */
    @Test
    public void sand() {
        //队列名称
        String queueName = "hello";
        String message = "hello world!";
        rabbitTemplate.convertAndSend(queueName,message);
        System.out.println("send a message to queue" + message);
    }

    /**
     * spring-amqp 向Work Queue 任务模型发送消息
     */
    @Test
    public void sandWorkQueue() throws InterruptedException {
        //队列名称
        String workQueueName = "work.hello";
        for (int i = 0; i < 50; i++) {
            String message = String.format("this is a message: Message %d",i);
            rabbitTemplate.convertAndSend(workQueueName,message);
            System.out.println("send a message to queue" + message);
            Thread.sleep(200);
        }
    }

    /**
     * spring-amqp 向 Exchange-Fanout 模型发送消息
     */
    @Test
    public void testFanoutExchange() {
        // 队列名称
        String exchangeName = "exchange.fanout";
        // 消息
        String message = "hello, everyone!";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    /**
     * spring-amqp 向 Exchange-Direct 模型发送消息
     */
    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "exchange.direct";
        // 消息
        String message = "红色警报！日本乱排核废水，导致海洋生物变异，惊现哥斯拉！";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "red", message+"啊啊啊啊");
        rabbitTemplate.convertAndSend(exchangeName, "yellow", message);

    }

    /**
     * spring-amqp 向 Exchange-Topic 模型发送消息
     */
    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "exchange.topic";
        // 消息
        String message = "喜报！孙悟空大战哥斯拉，胜!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "american.test.news", message);
    }

    /**
     * spring-amqp 消息转换器，对象序列号与反序列化
     * @throws InterruptedException
     */
    @Test
    public void testSendMap() throws InterruptedException {
        // 准备消息
        Map<String,Object> msg = new HashMap<>();
        msg.put("name", "Jack");
        msg.put("age", 21);
        // 发送消息
        rabbitTemplate.convertAndSend(QUEUE_NAME, msg);
    }
}
