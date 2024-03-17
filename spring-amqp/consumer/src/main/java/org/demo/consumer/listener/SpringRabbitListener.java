package org.demo.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitListener{
    @RabbitListener(queues = "hello")
    public void listenHello(String message){
        System.out.println(" [*] receive: " + message);
    }
}
