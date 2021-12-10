package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.moviecurators.domain.accounts.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //public void save(User user);

    User findOneById(Long id);

    //List<User> findAll();

    Optional<User> findOneByUsername(String username);

    List<User> findByUsername(String username);


}
