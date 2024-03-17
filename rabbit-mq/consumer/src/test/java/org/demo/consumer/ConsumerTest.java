package org.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class ConsumerTest {

    private final static String QUEUE_NAME = "hello";
    @Test
    public void receive() {
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
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }
}
