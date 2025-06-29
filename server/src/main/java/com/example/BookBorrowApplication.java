package com.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableTransactionManagerment
@Slf4j
@EnableCaching
@EnableScheduling
@MapperScan("com.example.mapper")
public class BookBorrowApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookBorrowApplication.class,args);
        log.info("server started");
    }
}
