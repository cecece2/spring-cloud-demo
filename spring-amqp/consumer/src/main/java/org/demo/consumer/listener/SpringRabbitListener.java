package org.demo.consumer.listener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class SpringRabbitListener{

    private final static String QUEUE_NAME = "hello";
    private final static String WORK_QUEUE_NAME = "work.hello";

    private static int count =0;
//    @RabbitListener(queues = "hello")
//    public void listenHello(String message){
//        System.out.println(" [*] receive: " + message);
//    }
    @RabbitListener(queues = "hello")
    public void listenWorkHello(String message) throws InterruptedException {
        System.out.println(" [listenWorkHello] receive: " + message + "  count:" + count++);

        Thread.sleep(600);
    }
    @RabbitListener(queues = "hello")
    public void listenWorkHello1(String message) throws InterruptedException {
        System.out.println(" [listenWorkHello1] receive: " + message);
        Thread.sleep(10);
    }
}
