package com.example.configuration;

import com.example.properties.AliOssProperties;
import com.example.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {

    //定义一个方法来返回AliOssUtils的Bean对象在配置类中
    @Bean
    @ConditionalOnMissingBean//用于判断当前的工具类Bean对象是否创建,已经有了就不需要再创建,工具类对象只需要一个
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        //方法名就是这个Bean对象的名字了,这里需要借助到配置属性类来进行获取配置文件中的属性值
        //这里是以形参的形式直接注入了AliOssProperties的对象在这个配置类中
        log.info("开始创建阿里云文件上传工具类对象: {}",aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint()
                , aliOssProperties.getAccessKeyId()
                , aliOssProperties.getAccessKeySecret()
                , aliOssProperties.getBucketName());
    }
}