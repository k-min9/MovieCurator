package ssafy.moviecurators.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.movies.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // 최신순, 평점순, 인기순
    List<Movie> findTop30ByOrderByReleaseDateDesc();
    List<Movie> findTop30ByOrderByVoteAverageDesc();
    List<Movie> findTop30ByOrderByPopularityDesc();

    // 검색 기능
    List<Movie> findTop25ByTitleContainingOrOriginalTitleContainingIgnoreCase(String searchKeyword, String searchKeyword2);

    // Home, 추천 영화 등 ID들 in절로 리스트 바인딩
    @Query("select m from Movie m where m.id in :ids") // in 절
    List<Movie> findByIds(@Param("ids") List<Long> ids);

    // 장르별 찾기 + fetchJoin
    @Query("select m from Movie m " +
            "join fetch m.movieGenres mg " +
            "join fetch mg.genre g " +
            "where g.name = :name " +
            "order by m.popularity desc")
    List<Movie> movieListGenre(@Param("name") String name, Pageable pageable);
}
