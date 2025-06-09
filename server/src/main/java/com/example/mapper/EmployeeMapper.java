package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.dto.EmployeePageQueryDTO;
import com.example.entity.Employee;
import com.example.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT id ,name as userName,password FROM book_borrow.employee WHERE name = #{username} ")
    Employee getByUsername(String username);

    @Options(useGeneratedKeys = true, keyProperty = "id") // 获取自增ID
    @Insert("INSERT INTO book_borrow.employee (name, password, sex, status, create_user, update_user) " +
            "VALUES (#{name}, #{password}, #{sex}, #{status}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("SELECT * FROM book_borrow.employee WHERE id = #{id}  ")
    Employee getById(Long id);
}
