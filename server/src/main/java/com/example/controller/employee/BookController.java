package com.example.controller.employee;

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

/**
 * 员工相关接口
 */
@RestController("employeeBookController")
@RequestMapping("/employee/book")
@Api(tags = "员工相关接口")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 新增书籍
     * @param bookDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增书籍")
    public Result save(@RequestBody BookDTO bookDTO){
        log.info("新增书籍:{}",bookDTO);
        bookService.save(bookDTO);
        return Result.success();
    }



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
     * 书籍批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("书籍批量删除")
    public Result delete(@RequestParam List<Long> ids){
        //使用注解@RequestParam可以让SpringMVC自动帮我们解析前端传过来的参数String类型的,然后根据,分隔成id
        log.info("书籍批量删除:{}",ids);
        //调用Service接口的方法来进行删除对应的菜品
        bookService.deleteBatch(ids);//批量删除的方法
        return Result.success();
    }

    /**
     * 根据id查询书籍
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询书籍")
    public Result<BookVO> getById(@PathVariable Long id){
        log.info("根据id查询书籍:{}",id);
        BookVO bookVO = bookService.getById(id);
        return Result.success(bookVO);
    }

    /**
     * 修改书籍
     * @param bookDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改书籍")
    public Result update(@RequestBody BookDTO bookDTO){
        log.info("修改书籍:{}",bookDTO);
        bookService.update(bookDTO);
        return Result.success();
    }

    /**
     * 根据分类id查询书籍
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询书籍")
    public Result<List<Book>> list(@PathVariable Long categoryId){
        List<Book> list = bookService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 书籍起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("书籍能否借阅状态修改")
    public Result<String> startOrStop(@PathVariable Integer status, Long id){
        bookService.startOrStop(status,id);
        return Result.success();
    }
}
