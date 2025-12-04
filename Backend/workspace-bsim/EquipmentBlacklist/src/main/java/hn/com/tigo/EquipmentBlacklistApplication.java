package hn.com.tigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class EquipmentBlacklistApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipmentBlacklistApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPIBlacklist() {
		return new OpenAPI().info(new Info().title("Equipment Blacklist Apis").version("1.0.0")
				.description("Equipment Blacklist Apis platform"));
	}

}
