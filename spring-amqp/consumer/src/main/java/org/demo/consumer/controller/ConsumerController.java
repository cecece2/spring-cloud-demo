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

    }

    @GetMapping({"/hi"})
    public String getMsg() {
        log.info("This is rabbitMQ consumer, hi!");
        return "This is rabbitMQ consumer, hi!";
    }

}
