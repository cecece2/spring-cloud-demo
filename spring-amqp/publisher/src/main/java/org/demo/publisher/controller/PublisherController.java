package org.demo.publisher.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/publish")
public class PublisherController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final static String QUEUE_NAME = "hello";

    @PostMapping("/send")
    public void send(@RequestParam(value = "msg") String msg){

        String message = "Hello World!" + msg;
        rabbitTemplate.convertAndSend( QUEUE_NAME, message);
        log.info(" [x] Sent '" + message + "'");

    }

    @GetMapping({"/hi"})
    public String getMsg() {
        log.info("This is rabbitMQ publisher, hi!");
        return "This is rabbitMQ publisher, hi!";
    }
}
