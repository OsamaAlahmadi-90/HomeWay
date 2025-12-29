package com.example.homeway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HomeWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeWayApplication.class, args);
    }

}
