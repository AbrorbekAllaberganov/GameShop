package com.example.Game;

import com.example.Game.payload.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameSiteApplication {
	@Bean
	public Result result(){
		return new Result();
	}

	public static void main(String[] args) {
		SpringApplication.run(GameSiteApplication.class, args);
	}

}
