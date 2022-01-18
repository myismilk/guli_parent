package com.wangkaiping.vdo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.wangkaiping")
@EnableDiscoveryClient
@EnableFeignClients
public class VdoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VdoApplication.class,args);
    }
}
