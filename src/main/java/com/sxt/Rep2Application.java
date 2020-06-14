package com.sxt;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages= {"com.sxt.system.mapper","com.sxt.business.mapper"})
public class Rep2Application {

	public static void main(String[] args) {
		SpringApplication.run(Rep2Application.class, args);
	}

}
