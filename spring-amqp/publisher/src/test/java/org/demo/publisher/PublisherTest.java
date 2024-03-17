package org.demo.publisher;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final static String QUEUE_NAME = "hello";
    private final static String WORK_QUEUE_NAME = "work.hello";
    @Test
    public void sand() {
        String message = "hello world!";
        rabbitTemplate.convertAndSend(QUEUE_NAME,message);
        System.out.println("send a message to queue" + message);
    }

    @Test
    public void sandWorkQueue() throws InterruptedException {


        for (int i = 0; i < 50; i++) {
            String message = String.format("this is a message: Message %d",i);
            rabbitTemplate.convertAndSend(QUEUE_NAME,message);
            System.out.println("send a message to queue" + message);
            Thread.sleep(200);
        }
    }
}
