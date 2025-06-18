package com.example.service.impl;

import com.example.constant.MessageConstant;
import com.example.dto.CategoryDTO;
import com.example.dto.CategoryPageQueryDTO;
import com.example.entity.Category;
import com.example.exception.BaseException;
import com.example.mapper.BookMapper;
import com.example.mapper.CategoryMapper;
import com.example.result.PageResult;
import com.example.service.CategoryService;
import com.example.vo.BookVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceimpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BookMapper bookMapper;
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        categoryMapper.insert(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total,records);
    }

    @Override
    public void deleteById(Integer id) {
        //删除分类
        categoryMapper.deleteById(id);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        categoryMapper.update(category);
    }



    @Override
    public List<Category> list() {
        List<Category> categories = categoryMapper.list();
        return categories;
    }

    @Override
    public boolean hasBooks(Integer id) {
        List<Integer> bookIds = bookMapper.getByCategoryId(id);
        if (bookIds != null && bookIds.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Category getById(Integer id) {
        Category category = categoryMapper.getById(id);
        if (category == null){
            throw new BaseException(MessageConstant.CATEGORY_NOT_FOUND);
        }
        return category;
    }
}
