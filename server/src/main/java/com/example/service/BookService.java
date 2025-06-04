package com.example.service;

import com.example.dto.BookDTO;
import com.example.dto.BookQueryDTO;
import com.example.entity.Book;
import com.example.result.PageResult;
import com.example.vo.BookVO;

import java.util.List;

public interface BookService {
    void save(BookDTO bookDTO);

    PageResult pageQuery(BookQueryDTO bookQueryDTO);

    void deleteBatch(List<Long> ids);

    BookVO getById(Long id);

    void update(BookDTO bookDTO);

    List<Book> list(Long categoryId);

    void startOrStop(Integer status, Long id);

    Book findById(Long bookId);

    void returnBook(Long bookId);
}
