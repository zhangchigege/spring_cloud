package com.zhangchi.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring_cloud_project
 * @description:
 * @author: ZhangChi
 * @create: 2019-09-04 14:45
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class DcController {

    public static void main(String[] args) {
        SpringApplication.run(DcController.class, args);
    }

    @RestController
    static class TestController {
        @Autowired
        DiscoveryClient discoveryClient;

        @GetMapping("/dc")
        public String dc() {
            String services = "Services" + discoveryClient.getServices();
            System.out.println(services);
            return services;
        }

    }


}
