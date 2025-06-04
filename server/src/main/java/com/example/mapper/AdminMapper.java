package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM book_borrow.admin WHERE name = #{userName}")
    Admin getByUserName(String userName);
}
