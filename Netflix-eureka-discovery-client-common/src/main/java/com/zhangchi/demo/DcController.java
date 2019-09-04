package com.zhangchi.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @program: spring_cloud_project
 * @description:
 * @author: ZhangChi
 * @create: 2019-09-04 14:45
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DcController {

    public static void main(String[] args) {
        SpringApplication.run(DcController.class, args);
    }

    @RestController
    static class TestController {
        @Autowired
        DiscoveryClient discoveryClient;
        @Autowired
        RestTemplate restTemplate;
        @Autowired
        WebClient.Builder webClientBuilder;
        @Autowired
        Client client;


        @GetMapping("/dc")
        public String dc() {
            String services = "Services" + discoveryClient.getServices();
            System.out.println(services);
            return services;
        }

        @GetMapping("/test1")
        public void invoke1() {
            String result = restTemplate.getForObject("http://alibaba-nacos-discovery-server/hello?name=didi", String.class);
            System.out.println(result);
        }

        @GetMapping("/test2")
        public Mono<String> invoke2() {
            Mono<String> result = webClientBuilder.build()
                    .get()
                    .uri("http://alibaba-nacos-discovery-server/hello?name=didi")
                    .retrieve()
                    .bodyToMono(String.class);
            return result;
        }
        @GetMapping("/test3")
        public String invoke3(){
            String result = client.hello("gege");
            return result;
        }


    }
//使用restTemplate拼接路径进行访问
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    //使用WebClient进行远程访问
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
    //使用Feign进行远程调用
    @FeignClient("alibaba-nacos-discovery-server")
    interface Client {
        @GetMapping("/hello")
        String hello(@RequestParam(name = "name") String name);
    }

}
