package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.BookQueryDTO;
import com.example.entity.Book;
import com.example.enumeration.OperationType;
import com.example.vo.BookVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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

    Page<BookVO> pageQuery(BookQueryDTO bookQueryDTO);

    @Select(
            "SELECT " +
                    "id, name, category_id AS categoryId, author, publish, edition, image, " +
                    "status, stock, create_time AS createTime, update_time AS updateTime, " +
                    "create_user AS createUser, update_user AS updateUser " +
                    "FROM book_borrow.book " +
                    "WHERE id = #{bookId}"
    )
    Book getById(Long bookId);

    @AutoFill(value = OperationType.UPDATE)
    void update(Book book);

    void deleteByIds(List<Long> ids);

    @Select("SELECT id FROM book_borrow.book WHERE category_id = #{id}")
    List<Long> getByCategoryId(Long id);
}
