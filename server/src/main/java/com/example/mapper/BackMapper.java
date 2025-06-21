package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.BackQueryDTO;
import com.example.entity.Back;
import com.example.enumeration.OperationType;
import com.example.vo.BackVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BackMapper {
    // 借阅记录动态查询：参数非空时过滤，空时跳过
    @Select("SELECT bw.*, bk.name as bookName, bk.author as author, bk.publish as publish, bk.edition as edition " +
            "FROM book_borrow.borrow bw " +
            "LEFT JOIN book_borrow.book bk ON bw.book_id = bk.id " +
            "WHERE bw.end_time is null " +
            "  AND ( #{userId} IS NULL OR bw.user_id = #{userId} ) " +
            "  AND ( #{startTime} IS NULL OR bw.start_time = #{startTime} ) " +
            "  AND ( #{endTime} IS NULL OR bw.end_time = #{endTime} ) ")
    Page<BackVO> pageQuery(BackQueryDTO backQueryDTO);
    // 如需获取自增ID，可添加选项（虽然当前方法是void返回）
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO book_borrow.back (brid, status) VALUES (#{brid}, #{status})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Back back);

    // 归还记录动态查询：参数非空时范围过滤，空时跳过
    @Select("SELECT " +
            "bc.id AS id, " +
            "bk.name AS bookName, " +
            "bu.name AS userName, " +
            "bc.status AS status, " +
            "bw.start_time, " +
            "bw.end_time " +
            "FROM book_borrow.back bc " +
            "LEFT JOIN book_borrow.borrow bw ON bc.brid = bw.id " +
            "LEFT JOIN book_borrow.book bk ON bw.book_id = bk.id " +
            "LEFT JOIN book_borrow.user bu ON bw.user_id = bu.id " +
            "WHERE bc.status = 0 " +
            "  AND ( #{userId} IS NULL OR bw.user_id = #{userId} ) " +
            "  AND ( #{categoryId} IS NULL OR bk.category_id = #{categoryId} ) " +
            "  AND ( #{startTime} IS NULL OR bw.start_time >= #{startTime} ) " +
            "  AND ( #{endTime} IS NULL OR bw.end_time <= #{endTime} ) ")
    Page<BackVO> list(BackQueryDTO backQueryDTO);

    @Select("SELECT id, brid, status FROM book_borrow.back WHERE id = #{id}")
    Back getById(Integer id);

    // 归还记录动态更新：非空字段更新，空字段保留
    @Update("UPDATE book_borrow.back " +
            "SET " +
            "brid = CASE WHEN #{brid} IS NOT NULL THEN #{brid} ELSE brid END, " +
            "status = CASE WHEN #{status} IS NOT NULL THEN #{status} ELSE status END " +
            "WHERE id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void update(Back back);
}
