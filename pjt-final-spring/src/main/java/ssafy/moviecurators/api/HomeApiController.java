package ssafy.moviecurators.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MovieDto>> home() {
        List<Movie> movies = movieService.homeMovie();
//        장고는 이 부분 애초에 작성을 안 함. 이거 작동 안 할 정도면 난리 난거긴 함. 그럴 경우 인기 상위 12개 출력
//        if(movies.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }

        List<MovieDto> result = movies.stream()
                .map(movie -> new MovieDto(movie))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }

    /**
     * home에 높은 점수의 상위 평가들 가져오기
     **/
    @GetMapping("/movies/articles/home/")
    public ResponseEntity<List<ArticleDto>> articleHome() {
        List<Article> movies = articleService.articleHome();

        List<ArticleDto> result = movies.stream()
                .map(article -> new ArticleDto(article))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }


}
