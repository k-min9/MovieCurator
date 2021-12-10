package ssafy.moviecurators;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MoviecuratorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviecuratorsApplication.class, args);
	}

}
