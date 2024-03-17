package org.demo.publisher.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping("/publish")
public class PublisherController {

    private final static String QUEUE_NAME = "hello";

    @PostMapping("/send")
    public void send(@RequestParam(value = "msg") String msg){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.31.43");
        factory.setUsername("hello");
        factory.setPassword("hello");
        factory.setVirtualHost("/hello");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!" + msg;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            log.info(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            log.error(String.format("sending message fail %s",e));
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            log.error(String.format("sending message fail by time out %s",e));
            throw new RuntimeException(e);
        }
    }

    @GetMapping({"/hi"})
    public String getMsg() {
        log.info("This is rabbitMQ publisher, hi!");
        return "This is rabbitMQ publisher, hi!";
    }
}
