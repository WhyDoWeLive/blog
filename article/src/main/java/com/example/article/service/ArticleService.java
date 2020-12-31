package com.example.article.service;

import com.example.article.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> queryArticle();
    Article queryArticleById(int articleId);
    int insertArticle(Article article);
    int updateArticle(Article article);
    int deleteArticle(int articleId);
}
