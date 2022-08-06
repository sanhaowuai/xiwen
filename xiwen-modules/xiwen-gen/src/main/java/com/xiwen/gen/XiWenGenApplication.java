package com.xiwen.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.xiwen.common.security.annotation.EnableCustomConfig;
import com.xiwen.common.security.annotation.EnableRyFeignClients;
import com.xiwen.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 代码生成
 * 
 * @author xiwen
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringBootApplication
public class XiWenGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiWenGenApplication.class, args);
        System.out.println("█▀▀▀▀▀▀▀██▀██▀▀▀█▀█▀▀▀▀▀▀▀█\n" +
                "█ █▀▀▀█ █ ▀▀  ▀█▀▄█ █▀▀▀█ █\n" +
                "█ █   █ █▀▄▄▄█ █ ▀█ █   █ █\n" +
                "█ ▀▀▀▀▀ █ ▄▀▄▀▄ █ █ ▀▀▀▀▀ █\n" +
                "█▀▀█▀██▀▀▄▄▀█▄ ▀▀██▀▀▀█▀▀██\n" +
                "███▀▀  ▀ █▀ ▄█▄█ █▄ ▄▄█▄▄██\n" +
                "█▄▄▀▄ ▀▀▀ ███▀▀ ▄▄ ▄ ██▄███\n" +
                "█▀█  ▄█▀▀ ▀ ▄ ▄█ ▄▄ █▄█▄▀▄█\n" +
                "█▀ █▀  ▀▄▀  ▀ █▀█ ▀ ▀▀ ▄▀▀█\n" +
                "█▀▀▀▀▀▀▀█ █▀██▄   █▀█ ██▀▄█\n" +
                "█ █▀▀▀█ ██ █▀▄  ▀ ▀▀▀ ▄▀▀██\n" +
                "█ █   █ █▄▀  █ ███▄▄▄▄▀▀█▀█\n" +
                "█ ▀▀▀▀▀ █ █ ▀▄█▀▄██▀▀▄▄█▀▀█\n" +
                "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n");
    }
}
