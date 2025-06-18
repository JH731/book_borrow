package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.CategoryPageQueryDTO;
import com.example.entity.Category;
import com.example.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("INSERT INTO book_borrow.category (name, create_time, update_time, create_user, update_user) " +
            "VALUES (#{category.name}, #{category.createTime}, #{category.updateTime}, " +
            "#{category.createUser}, #{category.updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "category.id", keyColumn = "id")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    @Select("SELECT * FROM book_borrow.category " +
            "WHERE (#{name} IS NULL OR name LIKE CONCAT('%', #{name}, '%'))")
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @Select("select * from book_borrow.category")
    List<Category> list();

    @Select("SELECT id FROM book_borrow.category WHERE name = #{categoryName} LIMIT 1")
    Integer getIdByName(String categoryName);

    @Update("UPDATE book_borrow.category " +
            "SET " +
            "name = CASE WHEN #{name} IS NOT NULL THEN #{name} ELSE name END, " +
            "update_time = CASE WHEN #{updateTime} IS NOT NULL THEN #{updateTime} ELSE update_time END, " +
            "update_user = CASE WHEN #{updateUser} IS NOT NULL THEN #{updateUser} ELSE update_user END " +
            "WHERE id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);


    @Select("select * from book_borrow.category where id = #{id}")
    Category getById(Integer id);

    @Delete("delete from book_borrow.category where id = #{id}")
    void deleteById(Integer id);
}
