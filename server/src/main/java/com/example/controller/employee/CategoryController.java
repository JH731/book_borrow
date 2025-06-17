package com.example.controller.employee;

import com.example.dto.CategoryDTO;
import com.example.dto.CategoryPageQueryDTO;
import com.example.entity.Category;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.CategoryService;
import com.example.vo.BookVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *员工相关接口
 */
@RestController("employeeCategoryController")
@RequestMapping("/employee/category")
@Api("员工相关接口")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(@RequestBody CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/list")
    public Result list() {
        List<Category> categoryList = categoryService.list();
        // 使用 Stream 提取 name 属性并收集为 List<String>
        List<String> categoryNames = categoryList.stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        return Result.success(categoryNames);
    }


    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<String> deleteById(@PathVariable Long id) throws Exception {
        log.info("删除分类：{}", id);
        if (categoryService.hasBooks(id)) {
            throw new Exception("该分类下存在图书，无法删除");
        }
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询分类")
    public Result<Category> getById(@PathVariable Long id){
        log.info("根据id查询分类:{}",id);
        Category category = categoryService.getById(id);
        return Result.success(category);
    }



}
