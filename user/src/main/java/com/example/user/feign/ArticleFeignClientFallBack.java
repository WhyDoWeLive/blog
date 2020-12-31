package com.example.user.feign;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleFeignClientFallBack implements ArticleFeignClient {
    public List getAllArticles() {
        return Lists.newArrayList("基于Feign的统一降级方法");
    }
}
