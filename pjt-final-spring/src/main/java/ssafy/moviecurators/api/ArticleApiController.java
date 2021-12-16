package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.domain.movies.Likes;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.error.ErrorResponse;
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
    private final MessageSource messageSource;

    /**
     * 평가 상세 페이지 메인 정보
     * */
    @GetMapping("/movies/{articleId}/article/")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("articleId") Long articleId, HttpServletRequest request) {

        Article article = articleRepository.getById(articleId);

        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().body(new ArticleDto(article));
    }

    /**
     * 영화에 사용자가 적은 평가 CRUD
     * get : 내가 적은 평가 가져오기
     * post : 평가 작성
     * put : 평가 수정
     * delete : 평가 삭제
     * 토큰 필요
     * */
    @GetMapping("/movies/{movieId}/articles/")
    public ResponseEntity articleDetail(@PathVariable("movieId") Long movieId, HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        Article article = articleService.articleDetail(movieId, userId);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().body(new ArticleDto(article));
    }

    @PostMapping("/movies/{movieId}/articles/")
    public ResponseEntity articleDetailPost(@PathVariable("movieId") Long movieId,
                                        @RequestBody Article article,
                                        HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        Article articleSaved = articleService.articleDetailPost(article, movieId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ArticleDto(articleSaved));
    }

    @PutMapping("/movies/{movieId}/articles/")
    public ResponseEntity articleDetailPut(@PathVariable("movieId") Long movieId,
                                        @RequestBody Article articleChange,
                                        HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        return ResponseEntity.ok().body(new SimpleArticleDto(articleService.articleDetailPut(articleChange, movieId, userId)));
    }

    @DeleteMapping("/movies/{movieId}/articles/")
    public ResponseEntity articleDetailDelete(@PathVariable("movieId") Long movieId, HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        articleService.articleDetailDelete(movieId, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    /**
     * 영화에 적힌 평가 다 가져오기
     * */
    @GetMapping("/movies/{movieId}/articles/list/")
    public ResponseEntity<List<ArticleDto>> articleList(@PathVariable("movieId") Long id) {
        List<Article> articles = articleService.articleList(id);
        if(articles.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<ArticleDto> result = articles.stream()
                .map(a -> new ArticleDto(a))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }

    /**
     * 해당 유저가 적은 평가 다 가져오기
     * */
    @GetMapping("/movies/{userId}/articles/curators/all/")
    public ResponseEntity<List<ArticleDto>> articleCuratorAll(@PathVariable("userId") Long id) {
        List<Article> articles = articleService.articleCuratorAll(id);
        if(articles.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<ArticleDto> result = articles.stream()
                .map(a -> new ArticleDto(a))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }

    /**
     * get : 좋아요 여부 확인
     * post : 좋아요
     * delete : 좋아요 해제
     * */
    @GetMapping("/movies/{articleId}/likes/")
    public ResponseEntity likesGet(@PathVariable("articleId") Long articleId,
                                   HttpServletRequest request){

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        Likes likes = articleService.likesGet(articleId, userId);
        if (likes == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().body(new SimpleLikesDto(likes));
    }


    @PostMapping("/movies/{articleId}/likes/")
    public ResponseEntity likes(@PathVariable("articleId") Long articleId,
                                      HttpServletRequest request){

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        articleService.likes(articleId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/movies/{articleId}/likes/")
    public ResponseEntity likesDelete(@PathVariable("articleId") Long articleId,
                                      HttpServletRequest request){

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        articleService.likesDelete(articleId, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    /**
     * 평가 포인트 갱신
     * */
    @PutMapping("/movies/donate/{articleId}/")
    public ResponseEntity pointChange(@PathVariable("articleId") Long articleId,
                           @RequestBody Map<String, String> obj,
                            HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }

        Integer mileageChange = Integer.parseInt(obj.get("mileage"));

        articleService.pointChange(articleId, mileageChange);

        return ResponseEntity.ok().body(null);
    }

}
