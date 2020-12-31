package com.example.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "article", fallback = ArticleFeignClientFallBack.class)
public interface ArticleFeignClient {
    //配置访问article的/getAllArticles路径
    @GetMapping(value = "/getAllArticles")
    List getAllArticles();
}
