package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.BackQueryDTO;
import com.example.entity.Back;
import com.example.enumeration.OperationType;
import com.example.vo.BackVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BackMapper {
    Page<BackVO> pageQuery(BackQueryDTO backQueryDTO);
    // 如需获取自增ID，可添加选项（虽然当前方法是void返回）
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO book_borrow.back (brid, status) VALUES (#{brid}, #{status})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Back back);

    Page<BackVO> list(BackQueryDTO backQueryDTO);

    @Select("SELECT id, brid, status FROM book_borrow.back WHERE id = #{id}")
    Back getById(Integer id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Back back);
}
