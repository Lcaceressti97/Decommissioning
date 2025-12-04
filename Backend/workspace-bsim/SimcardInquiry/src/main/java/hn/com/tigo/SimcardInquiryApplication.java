package hn.com.tigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SimcardInquiryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimcardInquiryApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Simcards Inquiry Apis").version("1.0.0")
				.description("Simcards Inquiry Apis platform"));
	}

}
