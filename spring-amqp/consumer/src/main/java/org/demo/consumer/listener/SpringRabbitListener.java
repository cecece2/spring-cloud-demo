package org.demo.consumer.listener;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class SpringRabbitListener{

    private final static String QUEUE_NAME = "hello";
    private final static String WORK_QUEUE_NAME = "work.hello";

    private static int count =0;
    /**
     * spring-amqp 从Basic Queue 简单队列获取消息
     */
    @RabbitListener(queues = QUEUE_NAME)
    public void listenHello(String message){
        System.out.println(" [*] receive: " + message);
    }

    /**
     * spring-amqp 从Work Queue 任务模型获取消息
     */
    @RabbitListener(queues = WORK_QUEUE_NAME)
    public void listenWorkHello(String message) throws InterruptedException {
        System.out.println(" [listenWorkHello] receive: " + message + "  count:" + count++);
        Thread.sleep(600);
    }
//    @RabbitListener(queues = WORK_QUEUE_NAME)
    @RabbitListener(queuesToDeclare = @Queue(WORK_QUEUE_NAME))
    public void listenWorkHello1(String message) throws InterruptedException {
        System.out.println(" [listenWorkHello-1] receive: " + message);
        Thread.sleep(10);
    }


    /**
     * spring-amqp 从exchange-fanout 发布订阅模型队列获取消息
     */
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) {
        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
    }
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) {
        System.out.println("消费者2接收到Fanout消息：【" + msg + "】");
    }



    /**
     * spring-amqp 从exchange-direct 模型中获取
     */
    @RabbitListener(bindings = @QueueBinding(
    value = @Queue(name = "direct.queue1"),
    exchange = @Exchange(name = "exchange.direct", type = ExchangeTypes.DIRECT),
    key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String msg){
        System.out.println("消费者接收到direct.queue1的消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "direct.queue2"),
        exchange = @Exchange(name = "exchange.direct", type = ExchangeTypes.DIRECT),
        key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String msg){
        System.out.println("消费者接收到direct.queue2的消息：【" + msg + "】");
    }

    /**
     * spring-amqp 从exchange-topic 模型中获取
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "exchange.topic", type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenTopicQueue1(String msg){
        System.out.println("消费者接收到topic.queue1的消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "exchange.topic", type = ExchangeTypes.TOPIC),
            key = "*.news"
    ))
    public void listenTopicQueue2(String msg){
        System.out.println("消费者接收到topic.queue2的消息：【" + msg + "】");
    }
}
