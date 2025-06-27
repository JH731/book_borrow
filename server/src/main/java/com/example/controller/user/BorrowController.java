package com.example.controller.user;

import com.example.context.BaseContext;
import com.example.dto.BookDTO;
import com.example.dto.BorrowQueryDTO;
import com.example.entity.*;
import com.example.exception.BaseException;
import com.example.mapper.BookMapper;
import com.example.mapper.CategoryMapper;
import com.example.mapper.UserMapper;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.BookService;
import com.example.service.BorrowService;
import com.example.service.UserService;
import com.example.vo.BorrowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController("userBorrowController")
@RequestMapping("/user/borrow")
@Slf4j
@Api(tags = "用户端相关接口")
public class  BorrowController {
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoryMapper categoryMapper;


    /**
     *借阅查询,其实就是分页查询(可以带条件)
     * @param borrowQueryDTO
     * @return
     */
    @ApiOperation("借阅记录分页查询")
    @GetMapping("/pageQuery")
    public Result<PageResult> pageQuery(BorrowQueryDTO borrowQueryDTO){
        PageResult pageResult = borrowService.pageQuery(borrowQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 借阅书籍
     * @param bookId
     * @return
     */
    @ApiOperation("借阅书籍")
    @PostMapping("/borrow/{bookId}")
    @Transactional
    public Result<?> borrowBook(@PathVariable Integer bookId) {
        Integer userID = BaseContext.getCurrentId();
        User user = userService.getById(userID);
        Book book = bookService.findById(bookId);

        // 校验库存和借阅次数
        if (book.getStock() <= 0) {
            throw new BaseException("图书库存不足");
        }
        if (borrowService.getCurrentBorrowCount(userID) >= userMapper.getMaxBorrowById(userID)) {
            throw new BaseException("用户已达到最大借阅数量");
        }

        //更新库存并创建记录
        book.setStock(book.getStock() - 1);
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book,bookDTO);
        Category category = categoryMapper.getById(book.getCategoryId());
        bookDTO.setCategoryName(category.getName());
        log.info("bookDTO: {}",bookDTO);
        bookService.update(bookDTO);
        Borrow record = new Borrow();
        record.setUserId(userID);
        record.setBookId(bookId);
        record.setStatus(1);
        record.setStartTime(LocalDateTime.now());
        record.setReturnTime(LocalDateTime.now().plusDays(7));
        log.info("Borrow: {}",record);
        borrowService.save(record);
        return Result.success();
    }


}
