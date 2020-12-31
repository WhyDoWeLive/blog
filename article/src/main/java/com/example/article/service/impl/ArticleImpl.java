package com.example.article.service.impl;

import com.example.article.dao.ArticleDao;
import com.example.article.entity.Article;
import com.example.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    // @Transactional：
    // 默认情况下Spring只会回滚运行时、未检查异常或者Error
    // 该注解只能应用到public方法
    // 同一个类中的方法间的调用会处在一个事务中
    @Transactional
    @Override
    public int insertArticle(Article article) {
        int effectedNum = articleDao.insertArticle(article);

        boolean rollbackTestFlag = false;
        if (rollbackTestFlag || effectedNum<0){
            throw new RuntimeException("插入Article失败");
        }
        return 0;
    }

    @Override
    public List<Article> queryArticle() {
        return articleDao.queryArticle();
    }

    @Override
    public Article queryArticleById(int articleId) {
        return articleDao.queryArticleById(articleId);
    }

    @Override
    public int updateArticle(Article article) {
        return 0;
    }

    @Override
    public int deleteArticle(int articleId) {
        return 0;
    }
}
