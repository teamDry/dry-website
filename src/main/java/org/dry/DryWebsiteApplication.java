package org.dry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.dry.mapper")
public class DryWebsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(DryWebsiteApplication.class, args);
    }

}
