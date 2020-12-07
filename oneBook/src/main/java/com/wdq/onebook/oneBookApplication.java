package com.wdq.onebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

//跨域设置
@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@MapperScan("com.wdq.onebook.dao")
@SpringBootApplication
public class oneBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(oneBookApplication.class, args);
	}

}