package com.example.user.HystrixCommand;

import com.google.common.collect.Lists;
import com.netflix.hystrix.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ArticleCommand extends HystrixCommand<List> {
    private RestTemplate restTemplate;

    public ArticleCommand(RestTemplate restTemplate){
        super(setter());
        this.restTemplate = restTemplate;
    }

    @Override
    protected List run() throws Exception {
        System.out.println("=======run's thread name====>" + Thread.currentThread().getName());
        return restTemplate.getForObject("http://127.0.0.1:9011/getAllArticles", List.class);
    }

    //必须是static类型，因为super()时是正在构造父类，不能引用到子类的setter()
    private static Setter setter(){
        //服务分组
        HystrixCommandGroupKey commandGroupKey = HystrixCommandGroupKey.Factory.asKey("article");
        //服务标示
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("get_all_article");
        //线程池名称
        HystrixThreadPoolKey threadPoolKey = HystrixThreadPoolKey.Factory.asKey("article_pool");

        HystrixThreadPoolProperties.Setter threadPoolProperties = HystrixThreadPoolProperties.Setter()
                .withCoreSize(2)
                .withKeepAliveTimeMinutes(15)
                .withQueueSizeRejectionThreshold(100);

        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                .withExecutionTimeoutEnabled(false);

        return Setter.withGroupKey(commandGroupKey).andCommandKey(commandKey).andThreadPoolKey(threadPoolKey)
                .andThreadPoolPropertiesDefaults(threadPoolProperties).andCommandPropertiesDefaults(commandProperties);
    }


    /**
     * 超出核心线程配置数的请求直接走该降级，不影响其他服务，即最多只有两个线程卡在article服务
     * @return
     */
    @Override
    protected List getFallback() {
        System.out.println("=======commandFallBack's thread name====>" + Thread.currentThread().getName());
        return Lists.newArrayList("fallback in command");
    }
}
