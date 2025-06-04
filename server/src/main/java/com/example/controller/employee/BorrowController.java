package com.example.controller.employee;

import com.example.dto.BorrowQueryDTO;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.BorrowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *管员工相关接口
 */
@RestController("employeeBorrowController")
@RequestMapping("/employee/borrow")
@Api(tags = "员工相关接口")
@Slf4j
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    /**
     *借阅申请查询
     * @param borrowQueryDTO
     * @return
     */
    @ApiOperation("借阅申请分页查询")
    @GetMapping("/borrowList")
    public Result<PageResult> borrowList(BorrowQueryDTO borrowQueryDTO){
        PageResult pageResult = borrowService.borrowList(borrowQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 借阅通过
     * @param id
     * @return
     */
    @PostMapping("/allow")
    @ApiOperation("借阅通过")
    public Result<String> allow(Integer id){
        borrowService.allow(id);
        return Result.success();
    }

    /**
     * 借阅不通过
     * @param id
     * @return
     */
    @PostMapping("/notAllow")
    @ApiOperation("借阅通过")
    public Result<String> notAllow(Integer id){
        borrowService.notAllow(id);
        return Result.success();
    }
}
