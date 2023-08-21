package com.digiwin.testdap.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * @author yzq
 * @version 1.0
 */

/**
 * demobackend 应用入口点
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TestEntryPoint {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(TestEntryPoint.class);
        springApplication.addListeners(new ApplicationPidFileWriter("SampleApp1EntryPoint.pid"));

        springApplication.setMainApplicationClass(TestEntryPoint.class);
        springApplication.run(args);
    }
}
