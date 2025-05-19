package com.xiaomi.practice.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.xiaomi.practice.infrastructure.mapper")
@ComponentScan(basePackages = {
        "com.xiaomi.practice.api",
        "com.xiaomi.practice.web",
        "com.xiaomi.common.base",
        "com.xiaomi.domain",
        "com.xiaomi.practice.infrastructure",
})
public class MiPracticeStartService {
    public static void main(String[] args) {
        SpringApplication.run(MiPracticeStartService.class, args);
    }
}
