package com.example.cutegreetings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 可爱猫咪问候服务 —— 三个接口，每个返回一张猫咪图片 + 一句软乎乎的问候。
 */
@SpringBootApplication
public class CuteGreetingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuteGreetingsApplication.class, args);
    }
}
