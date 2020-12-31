package com.example.article.controller;

import com.example.article.config.dao.DataSourceConfiguration;
import com.example.article.entity.Article;
import com.example.article.service.ArticleService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class ArticleController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.cloud.client.ip-address}")
    private String ip;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getAllArticles")
    public List<String> getAllArticles() throws InterruptedException {
        Thread.sleep(30000L);
        return Lists.newArrayList("Superman", "Tom and jerry from ip: " + ip + ", port: " + port);
    }

    @GetMapping("/listArticles")
    public List<Article> listArticles() {
        return articleService.queryArticle();
    }

    @GetMapping("/insertArticle")
    public void insertArticle() {
        Article article = new Article();
        article.setArticleName("当幸福来敲门");
        article.setScore(new BigDecimal(4.0));
        articleService.insertArticle(article);
    }


    ////////////////////////////测试///////////////////////
    @Autowired
    DataSourceConfiguration configuration;

    @GetMapping("/whatever")
    public String whatever(){
        return configuration.getJdbcUrl();
    }
}
