package com.example.controller.user;

import com.example.entity.Category;
import com.example.result.Result;
import com.example.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "用户端相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类
     * @param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询分类")
    public Result<List<Category>> list() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }
}
