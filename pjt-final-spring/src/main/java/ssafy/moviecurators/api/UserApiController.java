package ssafy.moviecurators.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // 회원가입 (DTO 사용)
    @PostMapping("/accounts/signup/")
    public CreateUserResponse saveMember(@RequestBody @Validated CreateUserRequest request) {

        //System.out.println("request = " + request);

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

}
