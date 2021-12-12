package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.ArticleDto;
import ssafy.moviecurators.dto.simple.SimpleMovieDto;
import ssafy.moviecurators.service.ArticleService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    /**
     * 영화에 사용자가 적은 평가 가져오기
     * */
//    @GetMapping("/movies/{movieId}/articles/")
//    public List<ArticleDto> articleDetail(@PathVariable("movieId") Long id) {
//        List<Article> articles = articleService.articleList(id);
//
//        List<ArticleDto> result = articles.stream()
//                .map(a -> new ArticleDto(a))
//                .collect(toList());
//        return result;
//    }


    /**
     * 영화에 적힌 평가 다 가져오기
     * */
    @GetMapping("/movies/{movieId}/articles/list/")
    public List<ArticleDto> articleList(@PathVariable("movieId") Long id) {
        List<Article> articles = articleService.articleList(id);

        List<ArticleDto> result = articles.stream()
                .map(a -> new ArticleDto(a))
                .collect(toList());
        return result;
    }



}
