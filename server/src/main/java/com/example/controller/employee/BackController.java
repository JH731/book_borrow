package com.example.controller.employee;

import com.example.dto.BackQueryDTO;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.BackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("employeeBackController")
@RequestMapping("/employee/back")
@Api(tags = "员工相关接口")
@Slf4j
public class BackController {

    @Autowired
    private BackService backService;
    /**
     *借阅查询,其实就是分页查询(可以带条件)
     * @param backQueryDTO
     * @return
     */
    @ApiOperation("归还申请分页查询")
    @GetMapping("/adminList")
    public Result<PageResult> adminList(@RequestBody BackQueryDTO backQueryDTO){
        PageResult pageResult = backService.adminList(backQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 归还通过
     * @param id
     * @return
     */
    @PostMapping("/allow")
    @ApiOperation("归还通过")
    public Result<String> allow(@PathVariable Integer id){
        backService.allow(id);
        return Result.success();
    }
}
