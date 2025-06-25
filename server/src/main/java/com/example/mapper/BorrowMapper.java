package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.BorrowQueryDTO;
import com.example.entity.Borrow;
import com.example.enumeration.OperationType;
import com.example.vo.AdminBorrowVO;
import com.example.vo.BorrowVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BorrowMapper {
    @Select("SELECT bw.id,bw.start_time as startTime,bw.end_time as endTime,bw.return_time as returnTime, bk.name as bookName, bk.author as author, bk.publish as publish, bk.edition as edition, ct.name AS categoryName, bu.name as userName " +
            "FROM book_borrow.borrow bw " +
            "LEFT JOIN book_borrow.book bk ON bw.book_id = bk.id " +
            "LEFT JOIN book_borrow.category ct ON bk.category_id = ct.id " +
            "LEFT JOIN book_borrow.user bu ON bw.user_id = bu.id " +
            "WHERE bw.status = 1 " +
            "  AND (#{userId} IS NULL OR bw.user_id = #{userId}) " +
            "  AND (#{categoryName} IS NULL OR #{categoryName} = '' OR ct.name = #{categoryName}) " +
            "  AND (#{bookName} IS NULL OR #{bookName} = '' OR bk.name = #{bookName}) " +
            "  AND (#{categoryId} IS NULL OR bk.category_id = #{categoryId}) " +
            "  AND (#{startTime} IS NULL OR bw.start_time = #{startTime}) " +
            "  AND (#{endTime} IS NULL OR bw.end_time = #{endTime}) ")
    Page<BorrowVO> pageQuery(BorrowQueryDTO borrowQueryDTO);


    @Select("SELECT id, status, user_id as userId, book_id as bookId, start_time as startTime, end_time as endTime, return_time as returnTime FROM book_borrow.borrow WHERE id = #{id}")
    Borrow getById(Integer id);


    @Update("UPDATE book_borrow.borrow " +
            "SET " +
            "status = CASE WHEN #{status} IS NOT NULL THEN #{status} ELSE status END, " +
            "user_id = CASE WHEN #{userId} IS NOT NULL THEN #{userId} ELSE user_id END, " +
            "book_id = CASE WHEN #{bookId} IS NOT NULL THEN #{bookId} ELSE book_id END " +
            "WHERE id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void update(Borrow borrow);

    @Select("SELECT bw.id,bw.start_time as startTime,bw.end_time as endTime,bw.return_time as returnTime, bk.name as bookName, bk.author as author, bk.publish as publish, bk.edition as edition, ct.name AS categoryName, bu.name as userName " +
            "FROM book_borrow.borrow bw " +
            "LEFT JOIN book_borrow.book bk ON bw.book_id = bk.id " +
            "LEFT JOIN book_borrow.category ct ON bk.category_id = ct.id " +
            "LEFT JOIN book_borrow.user bu ON bw.user_id = bu.id " +
            "WHERE bw.status = 0 " +
            "  AND (#{userId} IS NULL OR bw.user_id = #{userId}) " +
            "  AND (#{userName} IS NULL OR #{userName} = '' OR bu.name = #{userName}) " +
            "  AND (#{categoryName} IS NULL OR #{categoryName} = '' OR ct.name = #{categoryName}) " +
            "  AND (#{categoryId} IS NULL OR bk.category_id = #{categoryId}) " +
            "  AND (#{startTime} IS NULL OR bw.start_time = #{startTime}) " +
            "  AND (#{endTime} IS NULL OR bw.end_time = #{endTime}) ")
    Page<BorrowVO> borrowList(BorrowQueryDTO borrowQueryDTO);

    @Select("SELECT id FROM book_borrow.borrow WHERE user_id = #{id}")
    List<Integer> getByUserId(Integer id);

    @Select("SELECT id FROM book_borrow.borrow " +
            "WHERE book_id IN (${idsStr})")
    List<Integer> getByBookIds(String idsStr);

    @Delete("DELETE FROM book_borrow.borrow WHERE id IN (${borrowIdStr})")
    void deleteIds(String borrowIdStr);


    @Insert("INSERT INTO book_borrow.borrow (status, user_id, book_id, start_time, return_time) " +
            "VALUES (#{status}, #{userId}, #{bookId}, " +
            "#{startTime},#{returnTime})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Borrow borrow);
}
