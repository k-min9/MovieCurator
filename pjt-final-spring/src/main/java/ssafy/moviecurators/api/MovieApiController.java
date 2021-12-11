package ssafy.moviecurators.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.ArticleDto;
import ssafy.moviecurators.dto.MovieDto;
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
            movies = movieService.movieListGenre();
        }

        List<MovieDto> result = movies.stream()
                .map(movie -> new MovieDto(movie))
                .collect(toList());

        return result;
    }


    /**
     * 내부 DTO
     */
    @Data
    static class MovieListRequest {
        private String filter;
    }
}
