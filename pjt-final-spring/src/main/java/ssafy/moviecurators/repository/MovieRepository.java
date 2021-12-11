package ssafy.moviecurators.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import ssafy.moviecurators.domain.movies.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findOneById(Long id);

    // 최신순, 평점순, 인기순
    List<Movie> findTop30ByOrderByReleaseDateDesc();
    List<Movie> findTop30ByOrderByVoteAverageDesc();
    List<Movie> findTop30ByOrderByPopularityDesc();

    // Home용 리스트파라미터 바인딩
    @Query("select m from Movie m where m.id in :ids") // in 절
    List<Movie> findByIds(@Param("ids") List<Long> ids);

    // 장르별 찾기?
//    @Query("select m from Movie m join Genre g where g.name = :name")
//    @Query("select m from Movie m where m.genre.name = :name")
//    @Query("select m from Movie m" +
//            "join fetch m.movieGenres mg" +
//            "join fetch mg.genre g" +
//            "where g.name = :name")
//    @Query("select m from Movie m join fetch m.movieGenres mg where mg.genre.name = :name")
    @Query("select m from Movie m " +
            "join fetch m.movieGenres mg " +
            "join fetch mg.genre g " +
            "where g.name = :name " +
            "order by m.popularity desc")
    List<Movie> movieListGenre(@Param("name") String name, Pageable pageable);


    List<Movie> findTop25ByTitleContainingOrOriginalTitleContainingIgnoreCase(String searchKeyword, String searchKeyword2);
}
