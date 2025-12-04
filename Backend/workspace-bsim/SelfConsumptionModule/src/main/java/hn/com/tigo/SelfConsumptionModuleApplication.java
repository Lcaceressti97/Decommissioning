package hn.com.tigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class SelfConsumptionModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelfConsumptionModuleApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Self-Consumption Module Apis").version("1.0.0")
				.description("Self-Consumption Apis platform"));
	}
}
