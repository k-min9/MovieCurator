package ssafy.moviecurators.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.movies.Movie;

import java.util.List;
import java.util.Optional;

/**
 * 영화 관련 Repository, JpaRepository상속
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findTop30ByOrderByReleaseDateDesc();
    List<Movie> findTop30ByOrderByVoteAverageDesc();
    List<Movie> findTop30ByOrderByPopularityDesc();

    List<Movie> findTop25ByTitleContainingOrOriginalTitleContainingIgnoreCase(String searchKeyword, String searchKeyword2);

    @Query("select m from Movie m where m.id in :ids")
    List<Movie> findByIds(@Param("ids") List<Long> ids);

    @Query("select m from Movie m " +
            "join fetch m.movieGenres mg " +
            "join fetch mg.genre g " +
            "where g.name = :name " +
            "order by m.popularity desc")
    List<Movie> movieListGenre(@Param("name") String name, Pageable pageable);
}
