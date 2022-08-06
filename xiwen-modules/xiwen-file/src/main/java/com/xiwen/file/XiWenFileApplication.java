package com.xiwen.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.xiwen.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 文件服务
 * 
 * @author xiwen
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class XiWenFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiWenFileApplication.class, args);
        System.out.println("█▀▀▀▀▀▀▀██▀██▀▀▀█▀█▀▀▀▀▀▀▀█\n" +
                "█ █▀▀▀█ █▀▀ ▄ ▀▀█▄█ █▀▀▀█ █\n" +
                "█ █   █ ██▄ ▄██▀ ██ █   █ █\n" +
                "█ ▀▀▀▀▀ █▀▄▀█▀▄▀█ █ ▀▀▀▀▀ █\n" +
                "█▀▀▀█▀▀▀▀ ▄ ▀▄▀▄  ▀▀███▀███\n" +
                "█ █▀ ▀█▀▀█▀▄▄█ █ ▀ ▀▄▄█ ▄██\n" +
                "█ ▀▄ ▀ ▀▄▄██▄▀▀▀ ▄▀▄▀▀█▄▄██\n" +
                "█▀██ ▀▀▀█ ▄█  █▀ ▀  ▄▄███▄█\n" +
                "█▀▄ █▀▄▀▀█▀▀▀ ▀▀█  ▀▀▀  ▀▀█\n" +
                "█▀▀▀▀▀▀▀█ ▀ ▄█▄▀  █▀█ ██ ▄█\n" +
                "█ █▀▀▀█ █ █▄█▄▀▄▀ ▀▀▀ ▄ ███\n" +
                "█ █   █ █▀▄▀ █▄█   ▄ ▄▀██▀█\n" +
                "█ ▀▀▀▀▀ █ █  ▄█ █▄ ▄▀█▄█ ▀█\n" +
                "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n");
    }
}
