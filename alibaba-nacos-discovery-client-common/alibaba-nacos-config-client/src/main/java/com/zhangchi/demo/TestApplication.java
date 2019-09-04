package com.zhangchi.demo;/**
 * @program: spring_cloud_project
 * @description:
 * @author: ZhangChi
 * @create: 2019-09-04 16:55
 **/

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring_cloud_project
 * @description:
 * @author: ZhangChi
 * @create: 2019-09-04 16:55
 **/
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Slf4j
    @RestController
    @RefreshScope
    static class TestController {
        @Value("${didispace.title:}")
        private String title;

        @GetMapping("/hello")
        public String hello() {
            return title;
        }

    }


}
