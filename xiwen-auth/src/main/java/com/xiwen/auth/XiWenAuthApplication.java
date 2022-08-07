package com.xiwen.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.xiwen.common.security.annotation.EnableRyFeignClients;

/**
 * 认证授权中心
 * 
 * @author xiwen
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class XiWenAuthApplication {
    private static final Logger LOG = LoggerFactory.getLogger(XiWenAuthApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(XiWenAuthApplication.class, args);
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .-----------------.\n" +
                "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                "| |  ____  ____  | || |     _____    | || | _____  _____ | || |  _________   | || | ____  _____  | |\n" +
                "| | |_  _||_  _| | || |    |_   _|   | || ||_   _||_   _|| || | |_   ___  |  | || ||_   \\|_   _| | |\n" +
                "| |   \\ \\  / /   | || |      | |     | || |  | | /\\ | |  | || |   | |_  \\_|  | || |  |   \\ | |   | |\n" +
                "| |    > `' <    | || |      | |     | || |  | |/  \\| |  | || |   |  _|  _   | || |  | |\\ \\| |   | |\n" +
                "| |  _/ /'`\\ \\_  | || |     _| |_    | || |  |   /\\   |  | || |  _| |___/ |  | || | _| |_\\   |_  | |\n" +
                "| | |____||____| | || |    |_____|   | || |  |__/  \\__|  | || | |_________|  | || ||_____|\\____| | |\n" +
                "| |              | || |              | || |              | || |              | || |              | |\n" +
                "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                " '----------------'  '----------------'  '----------------'  '----------------'  '----------------' \n");
    }
}
