package com.schedular.app;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//Configuration
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	//http://localhost:8080/swagger-ui.html
	//http://localhost:8080/v2/api-docs
	@Bean
	public Docket api() {
		HashSet<String> consumesAndProduces = 
				new HashSet<String>(Arrays.asList("application/json", "application/xml"));
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(metadata())
				.consumes(consumesAndProduces)
				.produces(consumesAndProduces)
				.pathMapping("/");
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder()
				.title("Resful Service for Schedular API")
				.description("My awesome API Description")
				.version("1.0")
				/*.contact(new Contact("Edward Tanko", "tankoedward@wordpress.com",
						"example@example.com"))*/
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
				.build();
	}
}
