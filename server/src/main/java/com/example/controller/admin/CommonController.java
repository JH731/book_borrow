package com.example.controller.admin;

import com.example.constant.MessageConstant;
import com.example.result.Result;
import com.example.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {

    //注入AliOss工具类,它已经成为Bean对象了
    @Autowired
    AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    //定义上传文件的方法,返回的是文件的绝对路径,注意参数的名字要和请求的参数一样
    public Result<String> upload(MultipartFile file){
        //输入日志
        log.info("文件上传: {}",file);
        //接收到文件之后需要上传到阿里云OSS中存储
        //调用AliOssUtils的upload方法
        try {
            //首先获取原始的文件名
            String originalFilename = file.getOriginalFilename();
            //然后通过截取字符串的方式,截取后面的后缀,也就是文件的类型
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //然后借助UUID工具类来创建新的文件名
            String objectName = UUID.randomUUID().toString() + extension;
            //返回的是一个绝对路径,这个方法有两个参数,一个是file文件的二进制形式,一个是文件上传之后的名字
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            //最后封装成Result对象返回给前端
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败: {}",e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);//返回一个Result对象,并且会是失败的,里面的信息有常量类来进行封装
    }
}
