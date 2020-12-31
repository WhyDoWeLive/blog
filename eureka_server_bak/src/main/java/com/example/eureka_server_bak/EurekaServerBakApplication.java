package com.example.eureka_server_bak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerBakApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerBakApplication.class, args);
    }

}
