package ssafy.moviecurators.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.accounts.User;

import ssafy.moviecurators.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@RequiredArgsConstructor  // 생성자 주입 처리
public class UserService {

    // 주입 안될경우 컴파일 시점에서 체크하게 final 추가
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Transactional
    public Long join(User user){
        // 중복 체크
        validateDuplicateUser(user);
        user.setPassword(user.getPassword());
        user.encodePassword(passwordEncoder);

        userRepository.save(user);
        return user.getId();
    }

    // 중복 회원 검증 로직
    private void validateDuplicateUser(User user) {
        List<User> findUsers = userRepository.findByUsername(user.getUsername());
        if(!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<User> findMembers() {
        return userRepository.findAll();
    }

    //회원 단 건 조회
    public User findOne(Long memberId) {
        return userRepository.findOneById(memberId);
    }

    //api용: @Transactional > 트랜잭션 시작 > 찾아서 영속성 컨텍스트에 > 트랜잭션 종료되면서 커밋되고 flush
    @Transactional
    public void update(Long id, String name) {
        User member = userRepository.findOneById(id);
        member.setUsername(name);
    }


}
