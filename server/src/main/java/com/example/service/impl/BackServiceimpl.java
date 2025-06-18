package com.example.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        borrow.setStatus(3);
        borrowMapper.update(borrow);
    }

    @Override
    public PageResult adminList(BackQueryDTO backQueryDTO) {
        Integer userId = BaseContext.getCurrentId();
        backQueryDTO.setUserId(userId);
        PageHelper.startPage(backQueryDTO.getPage(),backQueryDTO.getPageSize());
        Page<BackVO> page = backMapper.list(backQueryDTO);
        long total = page.getTotal();
        List<BackVO> records = page.getResult();
        return new PageResult(total,records);
    }

    @Transactional
    @Override
    public void allow(Integer id) {
        //先根据id查询back,然后修改back的状态status
        Back back = backMapper.getById(id);
        back.setStatus(1);
        backMapper.update(back);
        //然后查询borrow表获取bookId
        Borrow borrow = borrowMapper.getById(back.getBrid());
        //根据bookId获取到book对象,修改其中的stock属性
        Book book = bookMapper.getById(borrow.getBookId());
        book.setStock(book.getStock() + 1);
        bookMapper.update(book);
    }
}
