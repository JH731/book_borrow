package com.example.controller.user;

import com.example.dto.BackQueryDTO;
import com.example.dto.BorrowQueryDTO;
import com.example.entity.Back;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.BackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userBackController")
@RequestMapping("/user/back")
@Api(tags = "用户端相关接口")
@Slf4j
public class BackController {

    @Autowired
    private BackService backService;
    /**
     *借阅查询,其实就是分页查询(可以带条件)
     * @param backQueryDTO
     * @return
     */
    @ApiOperation("可归还书籍页面分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(BackQueryDTO backQueryDTO){
        PageResult pageResult = backService.pageQuery(backQueryDTO);
        return Result.success(pageResult);
    }


    @ApiOperation("添加归还借阅记录")
    @PostMapping("/add")
    public Result add(Integer id){
        Back back = new Back();
        back.setBrid(id);
        backService.save(back);
        return Result.success();
    }
}
