package com.example.article.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Article {
    private Integer articleId;
    private String articleName;
    private BigDecimal score;
    private Date publishTime;
    private Date lastEditTime;
}
