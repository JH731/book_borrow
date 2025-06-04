package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.BorrowQueryDTO;
import com.example.entity.Borrow;
import com.example.enumeration.OperationType;
import com.example.vo.AdminBorrowVO;
import com.example.vo.BorrowVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BorrowMapper {
    Page<BorrowVO> pageQuery(BorrowQueryDTO borrowQueryDTO);


    @Select("SELECT * FROM book_borrow.borrow WHERE id = #{id}")
    Borrow getById(Integer id);


    @AutoFill(value = OperationType.UPDATE)
    //todo returnTime还没有进行处理
    void update(Borrow borrow);

    Page<AdminBorrowVO> borrowList(BorrowQueryDTO borrowQueryDTO);

    @Select("SELECT id FROM book_borrow.borrow WHERE user_id = #{id}")
    List<Long> getByUserId(Long id);

    List<Long> getByBookIds(List<Long> ids);

    void deleteIds(List<Long> borrowIds);


    @Insert("INSERT INTO book_borrow.borrow (status, user_id, book_id, start_time, end_time, return_time) " +
            "VALUES (#{borrow.status}, #{borrow.userId}, #{borrow.bookId}, " +
            "NOW(), #{borrow.endTime}, #{borrow.returnTime})")
    @Options(useGeneratedKeys = true, keyProperty = "borrow.id", keyColumn = "id")
    @AutoFill(value = OperationType.INSERT)
    void insert(Borrow borrow);
}
