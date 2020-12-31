package com.example.article.dao;

import com.example.article.entity.Article;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ArticleDaoTest {
    @Autowired
    private ArticleDao articleDao;

    @Test
    void queryArticle() {
        List<Article> articles = articleDao.queryArticle();
        System.out.println("article size: " + articles.size());
        System.out.println(articles);
    }

    @Test
    void queryArticleById() {
        Article article = articleDao.queryArticleById(2);
        System.out.println("query article by id: " + article);
    }

    @Test
    void insertArticle() {
        Article article = new Article();
        article.setArticleName("人性的弱点1");
        article.setScore(new BigDecimal(9.5));
        int effectedNum = articleDao.insertArticle(article);
        System.out.println("成功插入条目：" + effectedNum);
    }

    @Test
    void updateArticle() {
        Article article = new Article();
        article.setArticleId(2);
        article.setArticleName("人性的弱点");
        article.setScore(new BigDecimal(9.55));
        int effectedNum = articleDao.updateArticle(article);
        System.out.println("成功更新条目：" + effectedNum);
    }

    @Test
    void deleteArticle() {
        articleDao.deleteArticle(2);
    }
}