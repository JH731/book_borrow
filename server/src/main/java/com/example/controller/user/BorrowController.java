package com.example.controller.user;

import com.example.context.BaseContext;
import com.example.dto.BookDTO;
import com.example.dto.BorrowQueryDTO;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.entity.BorrowDetail;
import com.example.entity.User;
import com.example.exception.BaseException;
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


    /**
     *借阅查询,其实就是分页查询(可以带条件)
     * @param borrowQueryDTO
     * @return
     */
    @ApiOperation("借阅记录分页查询")
    @GetMapping("/pageQueryh")
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
    public Result<?> borrowBook(@PathVariable Long bookId) {
        Long userID = BaseContext.getCurrentId();
        User user = userService.getById(userID);
        Book book = bookService.findById(bookId);

        // 校验库存和借阅次数
        if (book.getStock() <= 0) {
            throw new BaseException("图书库存不足");
        }
        if (borrowService.getCurrentBorrowCount(userID) >= user.getMaxBorrow()) {
            throw new BaseException("用户已达到最大借阅数量");
        }

        //todo 这些操作都是放在Service中进行的
        //更新库存并创建记录
        book.setStock(book.getStock() - 1);
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book,bookDTO);
        bookService.save(bookDTO);

        Borrow record = new Borrow();
        record.setUserId(userID);
        record.setBookId(bookId);
        record.setStartTime(LocalDateTime.now());
        //todo 先默认设置只可以借阅7天
        record.setEndTime(LocalDateTime.now().plusDays(7));
        borrowService.save(record);

        return Result.success();
    }


}
