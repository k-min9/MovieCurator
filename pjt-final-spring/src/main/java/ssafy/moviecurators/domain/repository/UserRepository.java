package ssafy.moviecurators.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @Autowired
    private EntityManager em;

    public void save(User user){
        em.persist(user);
    }

//    public User findOne(Long id);

    public User find(Long id){
        return em.find(User.class, id);
    }
}
