package com.nunar.nunar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class NunArServerV1Application {

    public static void main(String[] args) {
        SpringApplication.run(NunArServerV1Application.class, args);
    }

}
