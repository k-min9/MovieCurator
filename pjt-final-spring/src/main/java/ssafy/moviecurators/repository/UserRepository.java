package ssafy.moviecurators.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

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

    public List<User> findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = : username", User.class)
                .setParameter("username", username)
                .getResultList();
    }
}
