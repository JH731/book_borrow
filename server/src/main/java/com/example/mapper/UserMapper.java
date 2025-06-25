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
    User getById(Integer userID);

    // 动态查询：name非空时模糊匹配，空时查全部
    @Select("SELECT user.* FROM book_borrow.user " +
            "WHERE user.name LIKE CONCAT('%', #{name}, '%') " +
            "   OR #{name} IS NULL")
    Page<User> pageQuery(UserPageQueryDTO userPageQueryDTO);


    // 动态更新：非空字段才更新，空字段保留原值
    @Update("UPDATE book_borrow.user " +
            "SET " +
            "name = CASE WHEN #{name} IS NOT NULL AND #{name} != '' THEN #{name} ELSE name END, " +
            "password = CASE WHEN #{password} IS NOT NULL THEN #{password} ELSE password END, " +
            "phone = CASE WHEN #{phone} IS NOT NULL THEN #{phone} ELSE phone END, " +
            "sex = CASE WHEN #{sex} IS NOT NULL THEN #{sex} ELSE sex END, " +
            "avatar = CASE WHEN #{avatar} IS NOT NULL THEN #{avatar} ELSE avatar END, " +
            "status = CASE WHEN #{status} IS NOT NULL THEN #{status} ELSE status END, " +
            "update_time = CASE WHEN #{updateTime} IS NOT NULL THEN #{updateTime} ELSE update_time END, " +
            "update_user = CASE WHEN #{updateUser} IS NOT NULL THEN #{updateUser} ELSE update_user END ," +
            "max_borrow = CASE WHEN #{maxBorrow} IS NOT NULL THEN #{maxBorrow} ELSE max_borrow END " +
            "WHERE id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    //todo 里面对应的updateTime的语句要删除
    void update(User user);




    @Insert("INSERT INTO book_borrow.user (name, password, phone, sex, avatar, status, create_time, create_user, update_time, update_user, max_borrow) " +
            "VALUES (#{name}, #{password}, #{phone}, #{sex}, #{avatar}, #{status}, #{createTime}, #{createUser}, #{updateTime}, #{updateUser}, #{maxBorrow})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @AutoFill(value = OperationType.INSERT)
    void insert(User user);

    @Select("select count(id) from book_borrow.user where phone = #{phone} ")
    Integer getCountByPhone(String phone);

    @Delete("DELETE FROM book_borrow.user WHERE id = #{id}")
    void delete(Integer id);
}
