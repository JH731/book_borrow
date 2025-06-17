package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.EmployeePageQueryDTO;
import com.example.entity.Employee;
import com.example.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT id ,name as userName,password FROM book_borrow.employee WHERE name = #{username} ")
    Employee getByUsername(String username);

    @Options(useGeneratedKeys = true, keyProperty = "id") // 获取自增ID
    @Insert("INSERT INTO book_borrow.employee (name, password, sex, status, create_user, update_user) " +
            "VALUES (#{name}, #{password}, #{sex}, #{status}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    @Select("SELECT employee.* FROM book_borrow.employee " +
            "WHERE employee.name LIKE CONCAT('%', #{name}, '%') " +  // name非空时，按模糊查询
            "   OR #{name} IS NULL")  // name为空时，OR条件成立，查询所有记录
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @Update("UPDATE book_borrow.employee " +
            "SET " +
            "name = CASE WHEN #{name} IS NOT NULL THEN #{name} ELSE name END, " +  // 非空则更新，空则保留原name
            "update_time = #{updateTime} " +  // 自动填充的更新时间（假设已通过AutoFill赋值）
            "WHERE id = #{id}")  // 按id定位要更新的员工
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("SELECT * FROM book_borrow.employee WHERE id = #{id}  ")
    Employee getById(Long id);
}
