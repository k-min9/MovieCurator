package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.movies.Likes;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.*;
import ssafy.moviecurators.dto.error.ErrorResponse;
import ssafy.moviecurators.dto.simple.SimpleMovieDto;
import ssafy.moviecurators.repository.MovieRepository;
import ssafy.moviecurators.service.JwtTokenProvider;
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

    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSource messageSource;

    /**
     * 단일 영화 가져오기
     * */
    @GetMapping("/movies/{id}/")
    public ResponseEntity<SimpleMovieDto> getMovie(@PathVariable("id") Long id) {
        Movie movie = movieRepository.getById(id);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().body(new SimpleMovieDto(movie));
    }

    /**
     * 추천 영화 가져오기
     * */
    @GetMapping("/movies/{id}/recommend/")
    public ResponseEntity<List<SimpleMovieDto>> getMoviesRecommend(@PathVariable("id") Long id) {
        try {
            List<Movie> recommendMovies = movieService.moviesRecommend(id);

            List<SimpleMovieDto> result = recommendMovies.stream()
                    .map(m -> new SimpleMovieDto(m))
                    .collect(toList());

            return ResponseEntity.ok().body(result);

        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * 장르별 영화가져오기
     **/
    @GetMapping("/movies/list/")
    public ResponseEntity<List<MovieDto>> movieList(HttpServletRequest request) {

        List<String> main = new ArrayList<>(Arrays.asList("release_date", "vote_average", "popularity"));
        String filter = request.getParameter("filter");
        List<Movie> movies;

        if (main.contains(filter)) {
            movies = movieService.movieListMain(filter);
        } else {
            movies = movieService.movieListGenre(filter);
        }
        if(movies.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<MovieDto> result = movies.stream()
                .map(movie -> new MovieDto(movie))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }

    /**
     * 영화 검색
     **/
    @GetMapping("/movies/search/")
    public ResponseEntity<List<MovieDto>> movieSearch(HttpServletRequest request) {
        String searchKeyword = request.getParameter("searchKeyword");
        List<Movie> movies = movieService.movieSearch(searchKeyword);
        if(movies.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<MovieDto> result = movies.stream()
                .map(movie -> new MovieDto(movie))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }

    /**
     * 좋아요 한 평가들 가져오기
     * */
    @GetMapping("/movies/likes/")
    public ResponseEntity likesList(HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        List<Likes> likes = movieService.likesList(userId);
        if(likes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<LikesDto> result = likes.stream()
                .map(like -> new LikesDto(like))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }

}
