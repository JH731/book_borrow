package com.example.service.impl;

import com.example.context.BaseContext;
import com.example.dto.BorrowQueryDTO;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.result.PageResult;
import com.example.service.BorrowService;
import com.example.vo.AdminBorrowVO;
import com.example.vo.BookVO;
import com.example.vo.BorrowStatisticVO;
import com.example.vo.BorrowVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BorrowServiceimpl implements BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private BookMapper bookMapper;
    @Override
    public PageResult pageQuery(BorrowQueryDTO borrowQueryDTO) {
        Long userId = BaseContext.getCurrentId();
        borrowQueryDTO.setUserId(userId);
        PageHelper.startPage(borrowQueryDTO.getPage(),borrowQueryDTO.getPageSize());
        Page<BorrowVO> page = borrowMapper.pageQuery(borrowQueryDTO);
        long total = page.getTotal();
        List<BorrowVO> records = page.getResult();
        return new PageResult(total,records);
    }

    @Override
    public BorrowStatisticVO statistics() {
        return null;
    }

    @Override
    public void statusUpdate(Integer status, Long id) {

    }

    @Override
    public BorrowVO details(Long id) {
        return null;
    }

    @Override
    public Integer getCurrentBorrowCount(Long userID) {
        return null;
    }

    @Override
    public void save(Borrow borrow) {
        borrowMapper.insert(borrow);
    }

    @Override
    public PageResult borrowList(BorrowQueryDTO borrowQueryDTO) {
        Long userId = BaseContext.getCurrentId();
        borrowQueryDTO.setUserId(userId);
        PageHelper.startPage(borrowQueryDTO.getPage(),borrowQueryDTO.getPageSize());
        Page<AdminBorrowVO> page = borrowMapper.borrowList(borrowQueryDTO);
        long total = page.getTotal();
        List<AdminBorrowVO> records = page.getResult();
        return new PageResult(total,records);
    }

    @Override
    public void allow(Integer id) {
        Borrow borrow = borrowMapper.getById(id);
        borrow.setStatus(1);
        borrowMapper.update(borrow);

    }

    @Transactional
    @Override
    public void notAllow(Integer id) {
        Borrow borrow = borrowMapper.getById(id);
        borrow.setStatus(2);
        borrowMapper.update(borrow);
        Book book = bookMapper.getById(borrow.getBookId());
        book.setStock(book.getStock() + 1);
        bookMapper.update(book);
    }
}
