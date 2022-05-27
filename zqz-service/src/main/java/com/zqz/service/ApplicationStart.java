package com.zqz.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: ApplicationStart
 * @Date: Created in 16:01 2022/4/29
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zqz.service"})
public class ApplicationStart {


    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
