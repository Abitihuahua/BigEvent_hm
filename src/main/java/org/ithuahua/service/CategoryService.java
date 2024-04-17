package org.ithuahua.service;

import org.ithuahua.pojo.Category;

import java.util.List;

public interface CategoryService {
    //新增分类
    void add(Category category);
    //列表查询
    List<Category> list();
    //根据id查询分类信息
    Category findById(Integer id);
    //更新类型信息
    void update(Category category);
    //删除类型信息
    void delete(Integer id);
}
