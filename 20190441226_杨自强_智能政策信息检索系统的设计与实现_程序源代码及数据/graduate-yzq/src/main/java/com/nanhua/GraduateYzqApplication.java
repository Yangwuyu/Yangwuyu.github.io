package com.nanhua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.Socket;

@SpringBootApplication
@MapperScan("com.nanhua.*.mapper")

public class GraduateYzqApplication {


    public static void main(String[] args) {
        SpringApplication.run(GraduateYzqApplication.class, args);
    }

}
