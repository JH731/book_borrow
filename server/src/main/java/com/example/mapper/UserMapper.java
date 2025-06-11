package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.UserPageQueryDTO;
import com.example.entity.User;
import com.example.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper{

    @Select("SELECT id ,name as userName,password FROM book_borrow.user WHERE name = #{userName}")
    User getByUserName(String userName);

    @Select(
            "SELECT " +
                    "id, name, password, phone, sex, avatar, status, " +
                    "create_time AS createTime, create_user AS createUser, " +
                    "update_time AS updateTime, update_user AS updateUser, " +
                    "max_borrow AS maxBorrow " +
                    "FROM book_borrow.user " +
                    "WHERE id = #{userID}"
    )
    User getById(Long userID);

    Page<User> pageQuery(UserPageQueryDTO userPageQueryDTO);


    @AutoFill(value = OperationType.UPDATE)
    //todo 里面对应的updateTime的语句要删除
    void update(User user);


    @Insert("INSERT INTO book_borrow.user (name, password, phone, sex, avatar, status, create_time, create_user, update_time, update_user, max_borrow) " +
            "VALUES (#{name}, #{password}, #{phone}, #{sex}, #{avatar}, #{status}, #{createTime}, #{createUser}, #{updateTime}, #{updateUser}, #{maxBorrow})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @AutoFill(value = OperationType.INSERT)
    void insert(User user);

    @Select("select count(id) from book_borrow.user where phone = #{phone} ")
    Long getCountByPhone(String phone);

    @Delete("DELETE FROM book_borrow.user WHERE id = #{id}")
    void delete(Long id);
}
