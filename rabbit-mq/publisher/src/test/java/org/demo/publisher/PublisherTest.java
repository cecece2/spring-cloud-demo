package org.demo.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class PublisherTest {

    private final static String QUEUE_NAME = "hello";
    @Test
    public void sand() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.31.43");
        factory.setUsername("hello");
        factory.setPassword("hello");
        factory.setVirtualHost("/hello");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
