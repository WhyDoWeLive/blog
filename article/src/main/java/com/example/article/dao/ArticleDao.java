package com.example.article.dao;

import com.example.article.entity.Article;

import java.util.List;

public interface ArticleDao {
    List<Article> queryArticle();
    Article queryArticleById(int articleId);
    int insertArticle(Article article);
    int updateArticle(Article article);
    int deleteArticle(int articleId);
}