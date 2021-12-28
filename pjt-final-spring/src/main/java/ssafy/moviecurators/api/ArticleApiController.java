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
     * 평가 상세 페이지 메인 정보[GET]
     * @param articleId 조회하려는 평가의 고유 id
     * @param request
     * @return http response body에 찾은 평가를 넣어서 보냄
     */
    @GetMapping("/movies/{articleId}/article/")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("articleId") Long articleId, HttpServletRequest request) {

        Article article = articleRepository.getById(articleId);

        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().body(new ArticleDto(article));
    }

    /**
     * 특정 영화에 달린 평가들을 조회 없다면 새로운 평가 양식 작성[GET]
     * JWT 검증
     * @param movieId 조회하려는 영화의 고유 id
     * @param request
     * @return 특정 영화에서 찾은 평가들을 http response body에 넣어서 보냄 없다면 새로운 평가 양식
     */
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

    /**
     * 평가의 상세 정보 페이지[POST]
     * JWT 검증
     * @param movieId 조회하려는 영화의 고유 id
     * @param article 사용자에 의해 새롭게 작성된 평가
     * @param request
     * @return 새롭게 작성된 평가를 검증 후에 http response body에 넣어서 보냄
     */
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

    /**
     * 영화에 달린 특정 평가를 수정[PUT]
     * @param movieId 수정하려는 영화의 고유 id
     * @param articleChange 수정하려는 평가 내용
     * @param request
     * @return 수정된 평가를 검증 후에 http response body에 넣어서 보냄
     */
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

    /**
     * 영화에 달린 특정 평가를 삭제[DELETE]
     * @param movieId 수정하려는 영화의 고유 id
     * @param request
     * @return 삭제된 평가를 http response body에 넣어서 보냄
     */
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
     * 영화에 적힌 평가 다 가져오기[GET]
     * @param id 평가를 보고 싶은 특정 영화의 id
     * @return 조회한 평가를 http response body에 넣어서 보냄
     */
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
     * 해당 유저가 적은 평가 다 가져오기[GET]
     * @param id 해당 유저의 고유 id
     * @return 해당 유저가 작성한 평가를 http response body에 넣어서 보냄
     */
    @GetMapping("/movies/{userId}/articles/curators/")
    public ResponseEntity<List<ArticleDto>> articleCurator(@PathVariable("userId") Long id) {
        List<Article> articles = articleService.articleCurator(id);
        if(articles.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<ArticleDto> result = articles.stream()
                .map(a -> new ArticleDto(a))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }

    /**
     *  해당 유저가 적은 평가 다 가져오기[GET]
     * 인피니티 스크롤(리턴값 Pageable 될 여지 있어서 분리)
     * @param id 해당 유저의 고유 id
     * @return 해당 유저가 작성한 평가를 http response body에 넣어서 보냄
     */
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
     * 좋아요 여부 확인[GET]
     * @param articleId 좋아요를 확인하기 위한 평가 id
     * @param request
     * @return 평가에 달린 좋아요를 http response body에 넣어서 보냄
     */
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

    /**
     * 좋아요 기능[POST]
     * @param articleId 좋아요를 누르기 위한 평가 id
     * @param request
     * @return 평가에 새로운 좋아요를 누르고 http response body에 넣어서 보냄
     */
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

    /**
     * 좋아요 해제[DELETE]
     * @param articleId 좋아요를 해제하기 위한 평가 id
     * @param request
     * @return 평가에 좋아요를 해제하고 http response body에 넣어서 보냄
     */
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
     * 평가 포인트 갱신[PUT]
     * @param articleId 포인트를 부여하고 싶은 평가 id
     * @param obj 원하는 금액의 포인트를 가지고 있는 collection
     * @param request
     * @return 해당 평가에 사용자로부터 후원받은 포인트를 갱신하고 http response body에 넣어서 보냄
     */
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
