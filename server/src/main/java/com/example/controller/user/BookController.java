package com.example.controller.user;

import com.example.dto.BookDTO;
import com.example.dto.BookQueryDTO;
import com.example.entity.Book;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.BookService;
import com.example.vo.BookVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userBookController")
@RequestMapping("/user/book")
@Api(tags = "用户端相关接口")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 分页查询书籍
     * @param bookQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询书籍")
    public Result<PageResult> page(BookQueryDTO bookQueryDTO){
        log.info("书籍分页插叙:{}",bookQueryDTO);
        PageResult pageResult = bookService.pageQuery(bookQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 根据id查询书籍
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询书籍")
    public Result<BookVO> getById(@PathVariable Integer id){
        log.info("根据id查询书籍:{}",id);
        BookVO bookVO = bookService.getById(id);
        return Result.success(bookVO);
    }


    /**
     * 根据分类id查询书籍
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询书籍")
    public Result<List<Book>> list(Integer categoryId){
        List<Book> list = bookService.list(categoryId);
        return Result.success(list);
    }

}