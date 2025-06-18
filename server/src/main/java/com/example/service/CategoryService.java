package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.CategoryPageQueryDTO;
import com.example.entity.Category;
import com.example.result.PageResult;

import java.util.List;

public interface CategoryService {
    void save(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteById(Integer id);

    void update(CategoryDTO categoryDTO);


    List<Category> list();

    boolean hasBooks(Integer id);

    Category getById(Integer id);
}
