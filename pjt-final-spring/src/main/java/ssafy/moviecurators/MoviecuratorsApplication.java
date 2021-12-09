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

//	// CORS 설정
//	@Configuration
//	public class WebConfig implements WebMvcConfigurer {
//
//		@Override
//		public void addCorsMappings(CorsRegistry registry) {
//			registry.addMapping("/**")  // 허락 URL 패턴
//					.allowedOrigins("*");  // 허락 Origin
//		}
//	}

}
