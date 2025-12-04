package hn.com.tigo.bsimmodule;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BsimModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BsimModuleApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("BSIM Apis").version("1.0.0")
                .description("BSIM Apis platform"));
    }
}
