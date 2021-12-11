package ssafy.moviecurators.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.repository.MovieRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MovieServiceTest {
    
    @Autowired
    MovieRepository movieRepository;
    
    @Test
    public void home영화12편(){
        List<Long> homeMovies = Arrays.asList(588228L, 508943L, 438631L, 566525L, 436969L, 550988L, 522402L, 497698L, 451048L, 459151L, 370172L, 482373L);
        List<Movie> results = movieRepository.findByIds(homeMovies);
        for (Movie result : results) {
            System.out.println("result.getTitle() = " + result.getTitle());
        }
    }

    @Test
    public void 영화전부() {
        List<Movie> results = movieRepository.findAll();
        for (Movie result : results) {
            System.out.println("result.getTitle() = " + result.getId());
        }
    }

}