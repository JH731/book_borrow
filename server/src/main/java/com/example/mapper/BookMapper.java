package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.BookQueryDTO;
import com.example.entity.Book;
import com.example.enumeration.OperationType;
import com.example.vo.BookVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    @Insert(
            "INSERT INTO book_borrow.book " +
                    "(name, category_id, author, publish, edition, image, " +
                    "status, stock, create_time, update_time, create_user, update_user) " +
                    "VALUES " +
                    "(#{name}, #{categoryId}, #{author}, #{publish}, #{edition}, #{image}, " +
                    "#{status}, #{stock}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @AutoFill(value = OperationType.INSERT)
    void insert(Book book);

    // 分页查询：纯MySQL语法（无动态标签）
    @Select("SELECT b.*, c.name as categoryName " +
            "FROM book_borrow.book b " +
            "LEFT JOIN book_borrow.category c ON b.category_id = c.id " +
            "WHERE b.stock > 0 " +
            "  AND ( #{name} IS NULL OR b.name LIKE CONCAT('%', #{name}, '%') ) " +
            "  AND ( #{categoryId} IS NULL OR b.category_id = #{categoryId} ) " +
            "  AND ( #{categoryName} IS NULL OR c.name = #{categoryName} ) ")
    Page<BookVO> pageQuery(BookQueryDTO bookQueryDTO);

    @Select(
            "SELECT " +
                    "id, name, category_id AS categoryId, author, publish, edition, image, " +
                    "status, stock, create_time AS createTime, update_time AS updateTime, " +
                    "create_user AS createUser, update_user AS updateUser " +
                    "FROM book_borrow.book " +
                    "WHERE id = #{bookId}"
    )
    Book getById(Integer bookId);

    @Update("UPDATE book_borrow.book " +
            "SET " +
            "name = CASE WHEN #{name} IS NOT NULL AND #{name} != '' THEN #{name} ELSE name END, " +
            "category_id = CASE WHEN #{categoryId} IS NOT NULL THEN #{categoryId} ELSE category_id END, " +
            "author = CASE WHEN #{author} IS NOT NULL THEN #{author} ELSE author END, " +
            "publish = CASE WHEN #{publish} IS NOT NULL THEN #{publish} ELSE publish END, " +
            "edition = CASE WHEN #{edition} IS NOT NULL THEN #{edition} ELSE edition END, " +
            "image = CASE WHEN #{image} IS NOT NULL THEN #{image} ELSE image END, " +
            "status = CASE WHEN #{status} IS NOT NULL THEN #{status} ELSE status END, " +
            "stock = CASE WHEN #{stock} IS NOT NULL THEN #{stock} ELSE stock END, " +
            "update_time = CASE WHEN #{updateTime} IS NOT NULL THEN #{updateTime} ELSE update_time END, " +
            "update_user = CASE WHEN #{updateUser} IS NOT NULL THEN #{updateUser} ELSE update_user END " +
            "WHERE id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void update(Book book);

    // 批量删除：纯MySQL语法（参数改为逗号分隔字符串）
    @Delete("DELETE FROM book_borrow.book WHERE id IN (${idsStr})")
    void deleteByIds(String idsStr);

    @Select("SELECT id FROM book_borrow.book WHERE category_id = #{id}")
    List<Integer> getByCategoryId(Integer id);
}
