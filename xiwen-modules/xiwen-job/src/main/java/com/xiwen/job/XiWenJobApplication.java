package com.xiwen.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.xiwen.common.security.annotation.EnableCustomConfig;
import com.xiwen.common.security.annotation.EnableRyFeignClients;
import com.xiwen.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 * 
 * @author xiwen
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringBootApplication
public class XiWenJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiWenJobApplication.class, args);
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
