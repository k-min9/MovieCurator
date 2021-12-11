package ssafy.moviecurators.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.ArticleDto;
import ssafy.moviecurators.dto.MovieDto;
import ssafy.moviecurators.service.ArticleService;
import ssafy.moviecurators.service.MovieService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class HomeApiController {

    private final MovieService movieService;
    private final ArticleService articleService;

    @GetMapping("/movies/")
    public List<MovieDto> home() {
        List<Movie> movies = movieService.homeMovie();
        List<MovieDto> result = movies.stream()
                .map(movie -> new MovieDto(movie))
                .collect(toList());

        return result;
    }

    /**
     * home에 높은 점수의 상위 평가들 가져오기
     **/
    @GetMapping("/movies/articles/home/")
    public List<ArticleDto> articleHome() {
        List<Article> movies = articleService.articleHome();
        List<ArticleDto> result = movies.stream()
                .map(article -> new ArticleDto(article))
                .collect(toList());
        return result;
    }


}
