package org.phonocardiogram;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "org.phonocardiogram.dao")
public class PhonocardiogramApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhonocardiogramApplication.class, args);
    }

}