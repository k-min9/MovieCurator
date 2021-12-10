package ssafy.moviecurators.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.EntityManager;
import java.util.List;

// 삭제 예정
@Repository
@RequiredArgsConstructor
public class UserRepositoryClass {

    @Autowired
    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public User findOneByUsername(String username) { return em.find(User.class, username);}

    public List<User> findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = : username", User.class)
                .setParameter("username", username)
                .getResultList();
    }
}
