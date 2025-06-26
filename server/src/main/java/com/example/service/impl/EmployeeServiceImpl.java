package com.example.service.impl;

import com.example.constant.MessageConstant;
import com.example.constant.PasswordConstant;
import com.example.constant.StatusConstant;
import com.example.context.BaseContext;
import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeLoginDTO;
import com.example.dto.EmployeePageQueryDTO;
import com.example.entity.Employee;
import com.example.exception.AccountLockedException;
import com.example.exception.AccountNotFoundException;
import com.example.exception.BaseException;
import com.example.exception.PasswordErrorException;
import com.example.mapper.EmployeeMapper;
import com.example.result.PageResult;
import com.example.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //处理各种异常情况(用户名不存在、密码不对、账号被锁定)
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());//根据byte来进行加密
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        //转化DTO对象为实体类Employee对象然后再调用Mapper接口
        Employee employee = new Employee();

        Employee employee1 = employeeMapper.getByUsername(employeeDTO.getName());
        if (employee1 != null) {
            throw new BaseException("该员工已重复");
        }

        //使用对象属性拷贝,借助Spring的工具类BeanUtils
        BeanUtils.copyProperties(employeeDTO,employee);

        //由于Employee的成员属性更多,所以还需要我们进行赋值
        //使用这里的常量类,不要使用硬编码
        //状态默认为1,1是正常,0是锁定无法登陆
        employee.setStatus(StatusConstant.ENABLE);
        String password = employeeDTO.getPassword();
        if (password == null) password = PasswordConstant.DEFAULT_PASSWORD;
        //设置密码注意需要加密,而且这里也用到常量类
        employee.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        //设置当前操作人的id
//        //使用ThreadLocal来获取该线程空间中在JWT令牌校验的时候解析出来的用户Id
//        Long currentId = BaseContext.getCurrentId();
//        employee.setCreateUser(currentId);
//        employee.setUpdateUser(currentId);

        //Emp对象创建成功后就可以调用Mapper接口里面的方法了
        employeeMapper.insert(employee);
    }

    /**
     * 员工的分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> records = page.getResult();
        return new PageResult(total,records);
    }

    @Override
    public void delete(Integer id) {
        employeeMapper.deleteByid(id);
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        //在之前的启用禁用接口中就设置了动态SQL进行修改员工的数据,这里需要转换EmployeeDTO对象即可
        Employee employee = new Employee();
        //使用对象属性拷贝的工具类BeanUtils
        BeanUtils.copyProperties(employeeDTO,employee);
        Integer currentId = BaseContext.getCurrentId();
        Employee employee1 = employeeMapper.getById(currentId);
        Employee employee2 = employeeMapper.getByUsername(employee.getName());
        if (employee2 != null) {
            if (employee1 != employee2) {
                //此时说明对应的名字已经存在数据库中了,并且是别人的信息
                throw new BaseException("该员工名已被别人创建");
            }
        }
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        employee.setPassword(password);

//        //由于我们执行的是修改操作,所以需要重新更新员工的updateTime和updateUser两个属性
//        employee.setUpdateTime(LocalDateTime.now());
//        //借助于这个BaseContext类来获取存放到当前线程空间中解析好的ID
//        //每一次请求都会携带JWT令牌都会进过JWT校验器进行分解获取其中的ID
//        employee.setUpdateUser(BaseContext.getCurrentId());

        //调用Mapper接口中的update方法,动态SQL语句进行修改员工信息
        employeeMapper.update(employee);
    }


    @Override
    public Employee getById(Integer id) {
        Employee employee = employeeMapper.getById(id);
        //隐藏密码,不让前端看到
//        employee.setPassword("****");
        return employee;
    }

}
