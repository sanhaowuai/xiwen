package com.xiwen.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 * 
 * @author xiwen
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class XiWenGatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(XiWenGatewayApplication.class, args);
        System.out.println("                    /\\            \n" +
                "                   //\\\\           \n" +
                "                  ||##||          \n" +
                "                 //##mm\\\\         \n" +
                "                //##*mmm\\\\        \n" +
                "               //###**mmm\\\\       \n" +
                "              //###***nmmm\\\\      \n" +
                "             //####***@nmmm\\\\     \n" +
                "             ||####***@nnmm||     \n" +
                "             ||####**@@@nnm||     \n" +
                "             |______________|     \n" +
                "             |    Krogg97   |     \n" +
                "              \\____________/      \n" +
                "               |          |       \n" +
                "              /|    /\\    |\\      \n" +
                "             /_|    || /\\ |_\\     \n" +
                "               |      NUSA|       \n" +
                "               |       \\/ |       \n" +
                "               |          |       \n" +
                "              /|    /\\    |\\      \n" +
                "             / |    ||    | \\     \n" +
                "            /  |    ||    |  \\    \n" +
                "           /  /\\    ||    /\\  \\   \n" +
                "          |__/  \\   ||   /  \\__|  \n" +
                "            /____\\      /____\\    \n" +
                "            |    |      |    |    \n" +
                "            |    |______|    |    \n" +
                "            |    | /--\\ |    |    \n" +
                "            |____|/----\\|____|    \n" +
                "             \\||/ //##\\\\ \\||/     \n" +
                "             /##\\//####\\\\/##\\     \n" +
                "            //##\\\\/####\\//##\\\\    \n" +
                "           ||/::\\||/##\\||/::\\||   \n" +
                "           \\\\\\''///:**:\\\\\\''///   \n" +
                "            \\\\\\///\\::::/\\\\\\///    \n" +
                "             \\\\//\\\\\\::///\\\\//     \n" +
                "              \\/\\\\\\\\..////\\/      \n" +
                "                 \\\\\\\\////         \n" +
                "                  \\\\\\///          \n" +
                "                   \\\\//           \n" +
                "                    \\/            ");
    }
}
