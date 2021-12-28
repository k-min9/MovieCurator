package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.domain.movies.Article;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 평가 관련 Repository, JpaRepository 상속
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findTop6ByCreatedBetweenOrderByPointsDesc(OffsetDateTime Start, OffsetDateTime end);

    @Query("select a from Article a join fetch a.movie m where m.id = :movieId order by a.points desc, a.id desc")
    List<Article> articleList(@Param("movieId") Long movieId);

    @Query("select a from Article a " +
            "join fetch a.movie m " +
            "join fetch a.user u " +
            "where m.id = :movieId and u.id = :userId")
    Article articleDetail(@Param("movieId") Long movieId, @Param("userId") Long userId);

    List<Article> findTop3ByUserOrderByIdDesc(User Id);

    List<Article> findByUserOrderByIdDesc(User Id);
}
