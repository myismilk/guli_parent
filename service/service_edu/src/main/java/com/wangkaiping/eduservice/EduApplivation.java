package com.wangkaiping.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wangkaiping"})
public class EduApplivation {
    public static void main(String[] args) {
        SpringApplication.run(EduApplivation.class,args);
    }
}