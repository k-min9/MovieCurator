package ssafy.moviecurators.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class WebSecurityBeanFactory {

//    private String passwordSecret = "SECRET_KEY";
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // encoding 기본형
//        String idForEncode = "pbkdf2";
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//
//        // parameter - 시크릿키, iteration(기본: 185000, 숫자가 낮을 수록 빨라짐), 해쉬 길이 - 기본: 256
//        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder(passwordSecret, 1, 256));
////        encoders.put("pbkdf2", new DjangoPbkdf2PasswordEncoder(passwordSecret, 1, 256));
//        return new DelegatingPasswordEncoder(idForEncode, encoders);
//    }
}
