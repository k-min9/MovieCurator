package ssafy.moviecurators.domain.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserTest {

    @Autowired
    EntityManager em;

    @Test
    public void 유저생성() {
        User user1 = new User("강민구", "111","강민구");
        User user2 = new User("임건호", "111","임건호");

        em.persist(user1);
        em.persist(user2);

        // 초기화
        em.flush();  // 쿼리 강제로 DB에 날리기
        em.clear();

        // 확인
        List<User> users = em.createQuery("select m from User m", User.class)
                .getResultList();

        for (User user : users) {
            System.out.println("user = " + user);
        }
    }

}