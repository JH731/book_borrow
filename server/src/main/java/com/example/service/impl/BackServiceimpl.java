package com.example.service.impl;

import com.aliyun.oss.ServiceException;
import com.example.context.BaseContext;
import com.example.dto.BackQueryDTO;
import com.example.entity.Back;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.mapper.BackMapper;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.result.PageResult;
import com.example.service.BackService;
import com.example.vo.BackVO;
import com.example.vo.BorrowVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class BackServiceimpl implements BackService {
    @Autowired
    private BackMapper backMapper;
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private BookMapper bookMapper;
    @Override
    public PageResult pageQuery(BackQueryDTO backQueryDTO) {
        Integer userId = BaseContext.getCurrentId();
        backQueryDTO.setUserId(userId);
        PageHelper.startPage(backQueryDTO.getPage(),backQueryDTO.getPageSize());
        Page<BackVO> page = backMapper.pageQuery(backQueryDTO);
        long total = page.getTotal();
        List<BackVO> records = page.getResult();
        return new PageResult(total,records);
    }

    @Transactional
    @Override
    public void save(Back back) {
        backMapper.insert(back);
        //修改此时借阅记录的状态,不能重复归还
        Borrow borrow = borrowMapper.getById(back.getBrid());
        borrow.setEndTime(LocalDateTime.now());
        borrow.setStatus(3);
        borrowMapper.update(borrow);
    }

    @Override
    public PageResult adminList(BackQueryDTO backQueryDTO) {
        log.info("backQueryDTO: {}",backQueryDTO);
        PageHelper.startPage(backQueryDTO.getPage(),backQueryDTO.getPageSize());
        Page<BackVO> page = backMapper.list(backQueryDTO);
        long total = page.getTotal();
        List<BackVO> records = page.getResult();
        log.info("查询返回的结果:{}",records);
        return new PageResult(total,records);
    }

    @Transactional
    @Override
    public void allow(Integer id) {
        // 1. 检查 back 是否存在
        Back back = backMapper.getById(id);
        log.info("Back: {}",back);
        if (back == null) {
            throw new ServiceException("需要申请的归还记录不存在或已归还，ID: " + id);
        }

        // 2. 更新 back 状态
        log.info("修改back状态为1");
        back.setStatus(1);
        backMapper.update(back);
        log.info("back: {}",back);

        // 3. 检查 borrow 记录是否存在
        Integer borrowId = back.getBrid();
        if (borrowId == null) {
            throw new ServiceException("归还记录未关联借阅记录，ID: " + id);
        }

        Borrow borrow = borrowMapper.getById(borrowId);
        log.info("Borrow: {}",borrow);
        if (borrow == null) {
            throw new ServiceException("关联的借阅记录不存在，ID: " + borrowId);
        }

        // 4. 检查图书是否存在
        Integer bookId = borrow.getBookId();
        if (bookId == null) {
            throw new ServiceException("借阅记录未关联图书，借阅ID: " + borrowId);
        }

        Book book = bookMapper.getById(bookId);
        if (book == null) {
            throw new ServiceException("关联的图书不存在，图书ID: " + bookId);
        }

        // 5. 安全更新库存
        book.setStock(book.getStock() + 1);
        bookMapper.update(book);
//        borrow.setStatus(3);
//        borrowMapper.update(borrow);
    }
}
