package com.epam.datastorage.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.epam")
@ComponentScan("com.epam")
@EntityScan(basePackages = {"com.epam"})
public class GlobalMentoringTask3DataStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalMentoringTask3DataStorageApplication.class, args);
    }

}

