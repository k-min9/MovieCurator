package ssafy.moviecurators.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MailService {

    private final UserRepository userRepository;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "movie_curators";

    public void sendMail(Long to_userId, Long from_userId, Integer mileageChange) {

        User from_user = userRepository.getById(from_userId);
        String title = "[Movie Curators] 후원을 받았습니다.";
        String content = "축하합니다!! \n " +
                "<"+ from_user.getNickname() + ">님으로부터 " + String.valueOf(mileageChange) + " 마일리지 후원을 받았습니다!! \n\n" +
                "Movie Curators를 이용해 주셔서 감사합니다.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("받을 메일을 입력해주세요 ex- aaa@gmail.com");
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }
}