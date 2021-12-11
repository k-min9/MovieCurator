package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.moviecurators.domain.movies.Article;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    //List<Article> findTop6ByOrderByPointsDesc();
    //List<Article> findByCreatedBetween(OffsetDateTime Start, OffsetDateTime end);
    List<Article> findTop6ByCreatedBetweenOrderByPointsDesc(OffsetDateTime Start, OffsetDateTime end);
}
