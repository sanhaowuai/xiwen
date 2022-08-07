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
