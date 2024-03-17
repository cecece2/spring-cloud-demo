package org.demo.consumer.controller;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/consumer")
@Slf4j
public class ConsumerController {

    private final static String QUEUE_NAME = "hello";

    @GetMapping("/hello")
    public void receive(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.31.43");
            factory.setUsername("hello");
            factory.setPassword("hello");
            factory.setVirtualHost("/hello");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                log.info(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
            log.info(" [*] Waiting for messages.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping({"/hi"})
    public String getMsg() {
        log.info("This is rabbitMQ consumer, hi!");
        return "This is rabbitMQ consumer, hi!";
    }

}
