package com.epam.finalDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinalDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalDemoApplication.class, args);
    }
}
