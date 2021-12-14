package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.moviecurators.domain.accounts.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findOneById(Long id);

    Optional<User> findOneByUsername(String username);

    List<User> findByUsername(String username);

    List<User> findTop6ByNicknameContainingOrUsernameContainingIgnoreCase(String searchKeyword, String searchKeyword1);
}
