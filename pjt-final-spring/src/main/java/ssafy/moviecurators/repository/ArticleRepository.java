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

public interface ArticleRepository extends JpaRepository<Article, Long> {

    //List<Article> findTop6ByOrderByPointsDesc();
    //List<Article> findByCreatedBetween(OffsetDateTime Start, OffsetDateTime end);
    List<Article> findTop6ByCreatedBetweenOrderByPointsDesc(OffsetDateTime Start, OffsetDateTime end);


    // 특정 영화에 적힌 모든 평가들
    @Query("select a from Article a join fetch a.movie m where m.id = :movieId order by a.points desc, a.id desc")
    List<Article> articleList(@Param("movieId") Long movieId);

    // 신청자가 영화에 적은 평가
    @Query("select a from Article a " +
            "join fetch a.movie m " +
            "join fetch a.user u " +
            "where m.id = :movieId and u.id = :userId")
    Article articleDetail(@Param("movieId") Long movieId, @Param("userId") Long userId);

    // 특정 유저가 적은 최신 평가 셋
    List<Article> findTop3ByUserOrderByIdDesc(User Id);

    // 특정 유저가 적은 모든 평가들 (Pageable로 전환 가능성 있음)
    List<Article> findByUserOrderByIdDesc(User Id);
}
