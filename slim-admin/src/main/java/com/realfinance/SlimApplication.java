package com.realfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;

@EnableWebMvc
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
@EnableTransactionManagement
@MapperScan(basePackages = {"com.realfinance.web.mapper"})
public class SlimApplication {
    public static void main(String[] args) {
        SpringApplication.run(SlimApplication.class, args);
        System.out.println("  ______   __  __               \n" +
                " /      \\ |  \\|  \\   slim framework start success   \n" +
                "|  $$$$$$\\| $$ \\$$ ______ ____  \n" +
                "| $$___\\$$| $$|  \\|      \\    \\ \n" +
                " \\$$    \\ | $$| $$| $$$$$$\\$$$$\\\n" +
                " _\\$$$$$$\\| $$| $$| $$ | $$ | $$\n" +
                "|  \\__| $$| $$| $$| $$ | $$ | $$\n" +
                " \\$$    $$| $$| $$| $$ | $$ | $$\n" +
                "  \\$$$$$$  \\$$ \\$$ \\$$  \\$$  \\$$\n");
    }
}
