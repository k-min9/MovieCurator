package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.accounts.Curator;



import java.util.Optional;

public interface CuratorRepository extends JpaRepository<Curator, Long> {

    @Query("select c from Curator c where c.toUser.id = :toUserId and c.fromUser.id = :fromUserId")
    Curator findCurator(@Param("toUserId") Long toUserId, @Param("fromUserId") Long fromUserId);

}
