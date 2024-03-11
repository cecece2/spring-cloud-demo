package com.demo.sayhello;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SayHelloApplication {

    private static Logger log = LoggerFactory.getLogger(SayHelloApplication.class);

    @Value("${server.port}")
    private String serverPort;
	public static void main(String[] args) {
		SpringApplication.run(SayHelloApplication.class, args);
	}

    @GetMapping("/greeting")
    public String greet() {
        log.info("Access /greeting");

        List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
        Random rand = new Random();

        int randomNum = rand.nextInt(greetings.size());
        return "say-hello"+serverPort+":"+greetings.get(randomNum);
    }

    @GetMapping("/")
        public String home() {
        log.info("Access /");
        return "Hi!";
    }
}