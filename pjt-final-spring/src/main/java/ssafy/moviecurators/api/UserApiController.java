package ssafy.moviecurators.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.moviecurators.domain.accounts.Curator;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.ArticleDto;
import ssafy.moviecurators.dto.CuratorDto;
import ssafy.moviecurators.dto.MovieDto;
import ssafy.moviecurators.dto.simple.SimpleUserDto;
import ssafy.moviecurators.repository.CuratorRepository;
import ssafy.moviecurators.service.JwtTokenProvider;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.dto.UserProfileDto;
import ssafy.moviecurators.repository.UserRepository;
import ssafy.moviecurators.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // application.yml 차후 수정 필요... 안 쓰는 듯?
    @Value("${file.dir}")
    private String fileDir;


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

    @PutMapping("/accounts/profile/")
    public SimpleUserDto updateProfile(@RequestPart(value = "image", required = false) MultipartFile file,
                              @RequestPart("nickname") String nickname,
                              @RequestPart("introduction") String introduction,
                              HttpServletRequest request) throws IOException {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        String image = "";
        if (file != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            User user = userRepository.getById(userId);
            String uploadDir = "media/profile/" + user.getId();

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("이미지 저장 불가: " + fileName, ioe);
            }

            image = "/media/profile/" + user.getId() + "/" + fileName;
        }

        return new SimpleUserDto(userService.updateProfile(userId, nickname, introduction, image));
    }

//    12/14 업로드 참조 원본
//    @PutMapping("/accounts/profile/")
//    public SimpleUserDto updateProfile2(@RequestPart(value = "image", required = false) MultipartFile file,
//                                       @RequestPart("nickname") String nickname,
//                                       @RequestPart("introduction") String introduction,
//                                       HttpServletRequest request) throws IOException {
//
//        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
//        Long userId = jwtTokenProvider.getUserIdFromJwt(token);
//
//        String image = "";
//        if (file != null) {
//            String fullPath = fileDir + file.getOriginalFilename();
//            image = fullPath;
//            System.out.println("주소 : " + fullPath);
//            file.transferTo(new File(fullPath));
//        }
//
//        return new SimpleUserDto(userService.updateProfile2(userId, nickname, introduction, image));
//    }

    /**
     * 신청자가 후원한 큐레이터들 가져오기
     * */
    @GetMapping("/accounts/curators/likes/")
    public List<CuratorDto> likesListCurator(HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long from_userId = jwtTokenProvider.getUserIdFromJwt(token);

        List<Curator> curators = userService.likesListCurator(from_userId);

        List<CuratorDto> result = curators.stream()
                .map(curator -> new CuratorDto(curator))
                .collect(toList());
        return result;
    }

    /**
     * 유저 검색
     **/
    @GetMapping("/accounts/search/")
    public List<SimpleUserDto> curatorSearch(HttpServletRequest request) {
        String searchKeyword = request.getParameter("searchKeyword");
        List<User> users = userService.curatorSearch(searchKeyword);

        List<SimpleUserDto> result = users.stream()
                .map(user -> new SimpleUserDto(user))
                .collect(toList());

        return result;
    }

    @GetMapping("/accounts/curators/{userId}/")
    public SimpleUserDto getCurator(@PathVariable("userId") Long id) {
        User user = userRepository.getById(id);

        return new SimpleUserDto(user);
    }


}
