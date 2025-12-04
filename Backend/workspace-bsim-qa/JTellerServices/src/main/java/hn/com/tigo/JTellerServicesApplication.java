package hn.com.tigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class JTellerServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(JTellerServicesApplication.class, args);
	}

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JTellerService Apis")
                        .version("1.0.0")
                        .description("JTellerService Apis platform"));
    }
	
}
