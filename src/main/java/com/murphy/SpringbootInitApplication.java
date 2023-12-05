package com.murphy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.murphy.mapper")//用通用mapper的scan扫描
public class SpringbootInitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootInitApplication.class, args);
	}

}
