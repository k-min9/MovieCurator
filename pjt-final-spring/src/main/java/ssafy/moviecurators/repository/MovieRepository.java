package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.movies.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findOneById(Long id);

    // Home용 리스트파라미터 바인딩
    @Query("select m from Movie m where m.id in :ids") // in 절
    List<Movie> findByIds(@Param("ids") List<Long> ids);


}
