package com.jitendra.homehelp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class HomeHelpManagementApplication implements ApplicationRunner {

	private static final Logger logger =   LogManager.getLogger(HomeHelpManagementApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(HomeHelpManagementApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
			logger.info("Home Help Management. Application Started....");
	}


}
