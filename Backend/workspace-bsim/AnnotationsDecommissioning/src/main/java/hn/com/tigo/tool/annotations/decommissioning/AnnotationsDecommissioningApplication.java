package hn.com.tigo.tool.annotations.decommissioning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AnnotationsDecommissioningApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnotationsDecommissioningApplication.class, args);
	}

}
