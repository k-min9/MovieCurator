package ssafy.moviecurators.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ssafy.moviecurators.dto.simple.SimpleUserDto;
import ssafy.moviecurators.service.JwtTokenProvider;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.dto.UserProfileDto;
import ssafy.moviecurators.repository.UserRepository;
import ssafy.moviecurators.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 (DTO)
     *
     */
    @PostMapping("/accounts/signup/")
    public CreateUserResponse saveMember(@RequestBody @Validated CreateUserRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());

        Long id = userService.join(user);
        return new CreateUserResponse(id);
    }

    // DTO는 로직 없으니 @Data 편하게 사용, static 필수
    @Data
    static class CreateUserResponse {
        private Long id;
        public CreateUserResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateUserRequest {
        private String username;
        private String nickname;
        private String password;
        //private String passwordConfirmation;
    }

    /**
     * 로그인 ; Request 부분 DTO화 해야함
     * */
    @PostMapping("/accounts/api-token-auth/")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        User member = userRepository.findOneByUsername(user.get("username"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이름 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken(member.getUsername(), member.getId());

        return ResponseEntity.ok(new LoginUserResponse(token));
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class LoginUserResponse {
        private String token;
        //private String tokenType = "Bearer ";
        public LoginUserResponse(String accessToken) {
            this.token = accessToken;
        }
    }

    @GetMapping("/accounts/{username}/get_user_info/")
    public UserProfileDto getUserInfo(@PathVariable("username") String username) {
        User user = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 정보가 없습니다."));
        return new UserProfileDto(user);
    }

    /**
     * 마일리지 충전
     * */
    @PutMapping("/accounts/mileage/")
    public SimpleUserDto mileageChange(@RequestBody Map<String, String> obj, HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        Integer mileageChange = Integer.parseInt(obj.get("mileage"));

        return new SimpleUserDto(userService.mileageChange(userId, mileageChange));
    }

    @PutMapping("/accounts/donate/{userId}/")
    public void donate(@PathVariable("userId") Long to_userId,
                       @RequestBody Map<String, String> obj,
                       HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long from_userId = jwtTokenProvider.getUserIdFromJwt(token);

        Integer mileageChange = Integer.parseInt(obj.get("mileage"));

        userService.donate(to_userId, from_userId, mileageChange);
    }




}
