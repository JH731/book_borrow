package com.example.configuration;

import com.example.Interceptor.JwtTokenAdminInterceptor;
import com.example.Interceptor.JwtTokenEmployeeInterceptor;
import com.example.Interceptor.JwtTokenInterceptor;
import com.example.Interceptor.JwtTokenUserInterceptor;
import com.example.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;
    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;
    @Autowired
    private JwtTokenEmployeeInterceptor jwtTokenEmployeeInterceptor;
    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**");

        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**");

        registry.addInterceptor(jwtTokenEmployeeInterceptor)
                .addPathPatterns("/employee/**");

        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("login/**")
                .excludePathPatterns("/login/login");
    }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    //这里针对用户端和管理端的包进行特定的扫描,然后进行分组写成两个接口文档便于区分
    @Bean
    public Docket docket1() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("图书管理系统接口文档")
                .version("2.0")
                .description("图书管理系统接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller.admin"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    @Bean
    public Docket docket2() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("图书管理系统接口文档")
                .version("2.0")
                .description("图书管理系统接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller.user"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    //在配置类这里重写extendMessageConverters(扩展消息转化器)

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //输出日志便于调试
        log.info("扩展消息转换器");

        //需要使用到Spring给我们提供的这个类来创建一个消息转化器对象
        MappingJackson2HttpMessageConverter coverter = new MappingJackson2HttpMessageConverter();
        //然后还需要给消息转化器设置一个对象转化器对象,这个对象转化器是我们自己创建的
        coverter.setObjectMapper(new JacksonObjectMapper());
        //最后还需要交给容器中,这样Spring才会执行这里面的对象转换规则
        converters.add(0,coverter);//这个容器是Spring中提供的,是一个集合里面已经有一些消息转化器了
        // ,所以需要设置序列为0,否则用不到

    }
}
