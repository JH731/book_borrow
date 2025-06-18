package com.example.service.impl;

import com.example.constant.MessageConstant;
import com.example.dto.BookDTO;
import com.example.dto.BookQueryDTO;
import com.example.entity.Book;
import com.example.entity.Employee;
import com.example.exception.DeletionNotAllowedException;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.mapper.CategoryMapper;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.BookService;
import com.example.vo.BookVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookServiceimpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public void save(BookDTO bookDTO) {
        Book book = new Book();
        BeanUtils.copyProperties(book,bookDTO);
        //根据传过来的categoryName获得对应的id
        Integer categoryId = categoryMapper.getIdByName(bookDTO.getCategoryName());
        book.setCategoryId(categoryId);
        bookMapper.insert(book);
    }

    @Override
    public PageResult pageQuery(BookQueryDTO bookQueryDTO) {
        PageHelper.startPage(bookQueryDTO.getPage(), bookQueryDTO.getPageSize());
        Page<BookVO> page = bookMapper.pageQuery(bookQueryDTO);

        long total = page.getTotal();
        List<BookVO> records = page.getResult();
        return new PageResult(total,records);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Integer> ids) {
        //首先查询是否和borrow表有关联,有就不能删除
        String idsStr = StringUtils.collectionToCommaDelimitedString(ids);
        List<Integer> borrowIds = borrowMapper.getByBookIds(idsStr);
        if (borrowIds != null && borrowIds.size() > 0) {
            String borrowIdStr = StringUtils.collectionToCommaDelimitedString(borrowIds);
            borrowMapper.deleteIds(borrowIdStr);
        }

        bookMapper.deleteByIds(idsStr);
    }

    @Override
    public BookVO getById(Integer id) {
        return null;
    }

    @Override
    public void update(BookDTO bookDTO) {
        Book book = new Book();
        BeanUtils.copyProperties(book,bookDTO);
        //根据传过来的categoryName获得对应的id
        Integer categoryId = categoryMapper.getIdByName(bookDTO.getCategoryName());
        book.setCategoryId(categoryId);
        bookMapper.update(book);
    }

    @Override
    public List<Book> list(Integer categoryId) {
        return null;
    }

    @Override
    public void startOrStop(Integer status, Integer id) {

    }

    @Override
    public Book findById(Integer bookId) {
        return null;
    }

    @Override
    public void returnBook(Integer bookId) {

    }
}
