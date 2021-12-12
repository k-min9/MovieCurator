package ssafy.moviecurators.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.ArticleDto;
import ssafy.moviecurators.dto.MovieDto;
import ssafy.moviecurators.dto.UserProfileDto;
import ssafy.moviecurators.dto.simple.SimpleMovieDto;
import ssafy.moviecurators.repository.MovieRepository;
import ssafy.moviecurators.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class MovieApiController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;

    /**
     * 단일 영화 가져오기
     * */
    @GetMapping("/movies/{id}/")
    public SimpleMovieDto getMovie(@PathVariable("id") Long id) {
        Movie movie = movieRepository.findOneById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화의 정보가 없습니다."));
        return new SimpleMovieDto(movie);
    }

    /**
     * 추천 영화 가져오기
     * */
    @GetMapping("/movies/{id}/recommend/")
    public List<SimpleMovieDto> getMoviesRecommend(@PathVariable("id") Long id) {
        List<Movie> recommendMovies = movieService.moviesRecommend(id);

        List<SimpleMovieDto> result = recommendMovies.stream()
                .map(m -> new SimpleMovieDto(m))
                .collect(toList());
        return result;
    }

    /**
     * 장르별 영화가져오기
     **/
    @GetMapping("/movies/list/")
    public List<MovieDto> movieList(HttpServletRequest request) {

        List<String> main = new ArrayList<>(Arrays.asList("release_date", "vote_average", "popularity"));
        String filter = request.getParameter("filter");
        List<Movie> movies;

        if (main.contains(filter)) {
            movies = movieService.movieListMain(filter);
        } else {
            movies = movieService.movieListGenre(filter);
        }

        List<MovieDto> result = movies.stream()
                .map(movie -> new MovieDto(movie))
                .collect(toList());

        return result;
    }

    /**
     * 영화 검색
     **/
    @GetMapping("/movies/search/")
    public List<MovieDto> movieSearch(HttpServletRequest request) {
        String searchKeyword = request.getParameter("searchKeyword");
        List<Movie> movies = movieService.movieSearch(searchKeyword);

        List<MovieDto> result = movies.stream()
                .map(movie -> new MovieDto(movie))
                .collect(toList());

        return result;
    }

}
