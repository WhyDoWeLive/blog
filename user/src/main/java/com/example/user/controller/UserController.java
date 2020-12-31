package com.example.user.controller;

import com.example.user.HystrixCommand.ArticleCommand;
import com.example.user.feign.ArticleFeignClient;
import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@DefaultProperties(defaultFallback = "defaultFallBack")
public class UserController {
    @Autowired
    private DiscoveryClient discoveryClient;  //通过SpringCloud提供的DiscoveryClient获取Eureka上微服务信息

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getDataFromEureka")
    public void getDataFromEureka(){
        List<ServiceInstance> instances = discoveryClient.getInstances("article");
        for (ServiceInstance instance : instances) {
            System.out.println(instance);     //可以获取到服务名为article的ip、port等信息
        }
    }

    @GetMapping("/getAllArticles")
    public List getAllArticles(){
        ServiceInstance instance = discoveryClient.getInstances("article").get(0);
        return restTemplate.getForObject("http://"+instance.getHost()+":"+instance.getPort()+"/getAllArticles", List.class);
    }

    @GetMapping("/getAllArticlesByRibbon")
    public List getAllArticlesByRibbon(){
        System.out.println("=======getAllArticlesByRibbon's thread name====>" + Thread.currentThread().getName());
        return restTemplate.getForObject("http://article/getAllArticles", List.class);
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Autowired
    private ArticleFeignClient articleFeignClient;

    @GetMapping("/getAllArticlesByFeign")
    public List getAllArticlesByFeign(){
        return articleFeignClient.getAllArticles();
    }

    @GetMapping("/getAllArticlesByFeignWithHystrix")
    @HystrixCommand//(fallbackMethod = "getAllArticlesFallBack")
    public List getAllArticlesByFeignWithHystrix(){
        return articleFeignClient.getAllArticles();
    }


    @GetMapping("/getAllArticlesByHystrixCommand")
    public List getAllArticlesByHystrixCommand(){
        System.out.println("=======getAllArticlesByHystrixCommand's thread name====>" + Thread.currentThread().getName());
        List<?> list = new ArticleCommand(restTemplate).execute();
        System.out.println("getAllArticlesByHystrixCommand finish, thread: " + Thread.currentThread().getName());
        return list;
    }


    /**
     * 降级方法：需与对应方法有相同类型的返回值
     */
    public List getAllArticlesFallBack(){
        return Lists.newArrayList("触发降级逻辑");
    }


    /**
     * Controller级别统一降级方法（不能有参数）
     */
    public List defaultFallBack(){
        return Lists.newArrayList("触发Controller级统一降级逻辑");
    }


    /**
     * helloWorld方法
     */
    @GetMapping("/helloWorld")
    public String helloWorld(){
        System.out.println("=======helloWorld's thread name====>" + Thread.currentThread().getName());
        return "Hello World";
    }
}
