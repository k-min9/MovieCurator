package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.accounts.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByUsername(String username);

    List<User> findByUsername(String username);

    List<User> findTop6ByNicknameContainingOrUsernameContainingIgnoreCase(String searchKeyword, String searchKeyword1);

    @Query("select u from User u where u.username = :username")
    User getByUsername(@Param("username") String username);
}
