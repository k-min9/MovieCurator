package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.simple.SimpleArticleDto;
import ssafy.moviecurators.dto.simple.SimpleLikesDto;
import ssafy.moviecurators.dto.simple.SimpleMovieDto;
import ssafy.moviecurators.repository.ArticleRepository;
import ssafy.moviecurators.repository.MovieRepository;
import ssafy.moviecurators.repository.UserRepository;
import ssafy.moviecurators.service.JwtTokenProvider;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.dto.ArticleDto;
import ssafy.moviecurators.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 평가 상세 페이지 메인 정보
     * */
    @GetMapping("/movies/{articleId}/article/")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("articleId") Long articleId, HttpServletRequest request) {

        Article article = articleRepository.getById(articleId);

        return ResponseEntity.ok().body(new ArticleDto(article));
    }

    /**
     * 영화에 사용자가 적은 평가 CRUD
     * get : 가져오기
     * post : 평가 작성
     * put : 평가 수정
     * delete : 평가 삭제
     * */
    @GetMapping("/movies/{movieId}/articles/")
    public ArticleDto articleDetail(@PathVariable("movieId") Long movieId, HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        Article article = articleService.articleDetail(movieId, userId);
        if (article != null) { return new ArticleDto(article);}
        else {return null;}
    }

    @PostMapping("/movies/{movieId}/articles/")
    public ArticleDto articleDetailPost(@PathVariable("movieId") Long movieId,
                                        @RequestBody Article article,
                                        HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        Article articleSaved = articleService.articleDetailPost(article, movieId, userId);
        return new ArticleDto(articleSaved);
    }

    @PutMapping("/movies/{movieId}/articles/")
    public SimpleArticleDto articleDetailPut(@PathVariable("movieId") Long movieId,
                                        @RequestBody Article articleChange,
                                        HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        return new SimpleArticleDto(articleService.articleDetailPut(articleChange, movieId, userId));
    }

    @DeleteMapping("/movies/{movieId}/articles/")
    public ResponseEntity<Long> articleDetailDelete(@PathVariable("movieId") Long movieId, HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        articleService.articleDetailDelete(movieId, userId);

        return new ResponseEntity<>(movieId, HttpStatus.OK);
    }

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

    /**
     * 해당 유저가 적은 평가 다 가져오기
     * */
    @GetMapping("/movies/{userId}/articles/curators/all/")
    public List<ArticleDto> articleCuratorAll(@PathVariable("userId") Long id) {
        List<Article> articles = articleService.articleCuratorAll(id);

        List<ArticleDto> result = articles.stream()
                .map(a -> new ArticleDto(a))
                .collect(toList());
        return result;
    }

    /**
     * get : 좋아요 여부 확인
     * post : 좋아요
     * delete : 좋아요 해제
     * */
    @GetMapping("/movies/{articleId}/likes/")
    public SimpleLikesDto likesGet(@PathVariable("articleId") Long articleId,
                                   HttpServletRequest request){

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        return new SimpleLikesDto(articleService.likesGet(articleId, userId));
    }


    @PostMapping("/movies/{articleId}/likes/")
    public ResponseEntity<Long> likes(@PathVariable("articleId") Long articleId,
                                      HttpServletRequest request){

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        articleService.likes(articleId, userId);
        
        return new ResponseEntity<>(articleId, HttpStatus.OK);
    }

    @DeleteMapping("/movies/{articleId}/likes/")
    public ResponseEntity<Long> likesDelete(@PathVariable("articleId") Long articleId,
                                      HttpServletRequest request){

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        articleService.likesDelete(articleId, userId);

        return new ResponseEntity<>(articleId, HttpStatus.OK);
    }

    /**
     * 평가 포인트 갱신
     * */
    @PutMapping("/movies/donate/{articleId}/")
    public void pointChange(@PathVariable("articleId") Long articleId,
                           @RequestBody Map<String, String> obj) {

        Integer mileageChange = Integer.parseInt(obj.get("mileage"));

        articleService.pointChange(articleId, mileageChange);
    }

}
