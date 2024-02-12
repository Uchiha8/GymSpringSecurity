package com.epam.finalDemo;

import com.epam.finalDemo.domain.Role;
import com.epam.finalDemo.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
public class FinalDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalDemoApplication.class, args);
    }
}
