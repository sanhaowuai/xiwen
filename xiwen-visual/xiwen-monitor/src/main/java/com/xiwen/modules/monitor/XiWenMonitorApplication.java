package com.xiwen.modules.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 监控中心
 * 
 * @author xiwen
 */
@EnableAdminServer
@SpringBootApplication
public class XiWenMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiWenMonitorApplication.class, args);
        System.out.println("█▀▀▀▀▀▀▀█▀▀▀█▀█████▀▀▀▀▀▀▀█\n" +
                "█ █▀▀▀█ █ ▄ ▀█ ▀ ▄█ █▀▀▀█ █\n" +
                "█ █   █ █▄▄█▄█▀█  █ █   █ █\n" +
                "█ ▀▀▀▀▀ █ █▀▄ █▀▄ █ ▀▀▀▀▀ █\n" +
                "█▀▀██▀▀▀██▄█▀▄▀ ▄▄██▀█▀▀▀▀█\n" +
                "███ █▀▄▀▄ ▄▄▀ ▀ ▄▀ ▄ ▀  ▀ █\n" +
                "█ ▄█▀▀█▀ ██ ▄▀ █ ▄▀██ █▀▄██\n" +
                "█▄▄▄▀▄█▀▀▀ ▀██▄▄▄▀██ ▀ █ ▀█\n" +
                "█▀▀██▀▄▀  ▄▀▀▄▀ █▀   ▀ █▀▀█\n" +
                "█▀▀▀▀▀▀▀█▄█▀▀ ▀▀█ █▀█  ██▀█\n" +
                "█ █▀▀▀█ █  ▄▄▀ █▀ ▀▀▀ ▄██ █\n" +
                "█ █   █ ██ ▄██▄▄▄ ▀█ ▀▄█ ▄█\n" +
                "█ ▀▀▀▀▀ █  ▄▀▄▀ ▄▀█▄▄█▄  ▀█\n" +
                "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n");
    }
}
