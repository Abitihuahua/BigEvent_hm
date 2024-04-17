package org.ithuahua.service;

import org.ithuahua.pojo.Article;
import org.ithuahua.pojo.PageBean;

public interface ArticleService {
    //新增文章
    void add(Article article);

    //分页查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
