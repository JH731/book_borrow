package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.CategoryPageQueryDTO;
import com.example.entity.Category;
import com.example.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("INSERT INTO book_borrow.category (name, create_time, update_time, create_user, update_user) " +
            "VALUES (#{category.name}, #{category.createTime}, #{category.updateTime}, " +
            "#{category.createUser}, #{category.updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "category.id", keyColumn = "id")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @Select("select * from book_borrow.category")
    List<Category> list();

    @Select("SELECT id FROM book_borrow.category WHERE name = #{categoryName} LIMIT 1")
    Long getIdByName(String categoryName);

    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    @Select("select * from book_borrow.category where id = #{id}")
    Category getById(Long id);
}
