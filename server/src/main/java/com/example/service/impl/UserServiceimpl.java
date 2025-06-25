package com.example.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.example.constant.MessageConstant;
import com.example.constant.StatusConstant;
import com.example.context.BaseContext;
import com.example.dto.RegisterDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.dto.UserPageQueryDTO;
import com.example.entity.Borrow;
import com.example.entity.Employee;
import com.example.entity.User;
import com.example.exception.*;
import com.example.mapper.BorrowMapper;
import com.example.mapper.UserMapper;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceimpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BorrowMapper borrowMapper;
//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;

    // 验证码有效期（分钟）
    private static final int CODE_EXPIRE_MINUTES = 5;


    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUserName(username);

        //处理异常
        if (user == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对,先进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())){
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //不能借书也可以登录
//        if (user.getStatus() == StatusConstant.DISABLE) {
//            //账号被锁定
//            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
//        }
        return user;
    }

    @Override
    public User getById(Integer userID) {
        User user = userMapper.getById(userID);
        //隐藏密码
        user.setPassword("****");
        return user;
    }

    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize());
        Page<User> page = userMapper.pageQuery(userPageQueryDTO);
        long total = page.getTotal();
        List<User> records = page.getResult();
        return new PageResult(total,records);
    }

    @Override
    public void startOrstop(Integer status, Integer id) {
        User user = User.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        //然后传递给Mapper接口调用对应的修改的SQL语句
        userMapper.update(user);
        //update user set status = ? where id = ?
    }

    @Override
    public void save(User user) {
        User user1 = userMapper.getByUserName(user.getName());
        if (user1 != null) {
            throw new BaseException("该用户已重复");
        }
        String password = user.getPassword();
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userMapper.insert(user);
    }

    @Override
    public Result sendVerificationCode(String phone) {
        return null;
    }

    @Override
    public Result register(RegisterDTO dto) {
        return null;
    }

    @Override
    public void update(UserDTO userDTO) {
        log.info("userDTO:{}",userDTO);
        User user = new User();
        BeanUtils.copyProperties(user,userDTO);
        userMapper.update(user);
        log.info("user:{}",user);
    }

    @Override
    public void updateByself(UserDTO userDTO) {
        log.info("userDTO:{}",userDTO);
        Integer userId = BaseContext.getCurrentId();
        User user = new User();
        user.setId(userId);
        BeanUtils.copyProperties(user,userDTO);
        userMapper.update(user);
        log.info("user:{}",user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        //需要先判断当前的用户是否有关联的借阅记录,如果有就不能删除
        //根据用户id查询是否有关联的借阅id
        List<Integer> borrowIds = borrowMapper.getByUserId(id);
        if (borrowIds != null && borrowIds.size() > 0) {
            // List<Long> → "1,2,3" 格式的字符串
            String borrowIdStr = StringUtils.collectionToCommaDelimitedString(borrowIds);
            borrowMapper.deleteIds(borrowIdStr);
        }
        userMapper.delete(id);
    }

//    @Override
//    public Result sendVerificationCode(String phone) {
//        // 1. 生成6位随机验证码
//        String code = RandomStringUtils.randomNumeric(6);
//        // 2. 存储到Redis（示例代码，实际需配置Redis服务器）
//        redisTemplate.opsForValue().set(
//                "SMS_CODE:" + phone,
//                code,
//                CODE_EXPIRE_MINUTES,
//                TimeUnit.MINUTES
//        );
//        // 3. 模拟发送验证码（实际替换为短信服务）
//        System.out.println("发送验证码：" + phone + " -> " + code);
//
//        return Result.success("验证码已发送");
//
//    }
//
//    @Override
//    public Result register(RegisterDTO dto) {
//        // 1. 校验验证码
//        String cacheKey = "SMS_CODE:" + dto.getPhone();
//        String cacheCode = redisTemplate.opsForValue().get(cacheKey);
//
//        if (cacheCode == null) {
//            return Result.error("验证码已过期");
//        }
//        if (!cacheCode.equals(dto.getCode())) {
//            return Result.error("验证码错误");
//        }
//        // 2. 检查手机号是否已注册
//        if (userMapper.getCountByPhone(dto.getPhone()) > 0){
//            return Result.error("该手机号已注册");
//        }
//
//        // 3. 创建用户
//        User user = new User();
//        user.setPhone(dto.getPhone());
//        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
//        user.setCreateTime(LocalDateTime.now());
//
//        userMapper.insert(user);
//
//        // 4. 清除验证码
//        redisTemplate.delete(cacheKey);
//
//        return Result.success("注册成功");
//    }
}
