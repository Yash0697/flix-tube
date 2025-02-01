package com.yash.flix_tube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlixTubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlixTubeApplication.class, args);
	}

}
