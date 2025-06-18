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

    void deleteBatch(List<Integer> ids);

    BookVO getById(Integer id);

    void update(BookDTO bookDTO);

    List<Book> list(Integer categoryId);

    void startOrStop(Integer status, Integer id);

    Book findById(Integer bookId);

    void returnBook(Integer bookId);
}
