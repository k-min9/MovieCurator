package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.accounts.Curator;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.domain.movies.Article;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface CuratorRepository extends JpaRepository<Curator, Long> {

    @Query("select c from Curator c where c.toUser.id = :toUserId and c.fromUser.id = :fromUserId")
    Curator findCurator(@Param("toUserId") Long toUserId, @Param("fromUserId") Long fromUserId);

    // 해당 지원자가 후원하는 후원자 최대 여섯
    List<Curator> findTop6ByFromUserOrderByScoreDesc(User fromUser);

}
