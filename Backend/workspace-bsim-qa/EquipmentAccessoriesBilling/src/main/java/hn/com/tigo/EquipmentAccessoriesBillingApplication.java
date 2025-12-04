package hn.com.tigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
public class EquipmentAccessoriesBillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipmentAccessoriesBillingApplication.class, args);
	}
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Equipment Accessories Billing Apis").version("1.0.0")
				.description("Equipment Accessories Billing Apis platform"));
	}

}
