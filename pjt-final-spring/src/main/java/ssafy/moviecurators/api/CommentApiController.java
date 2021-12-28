package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.CommentDto;
import ssafy.moviecurators.dto.error.ErrorResponse;
import ssafy.moviecurators.dto.simple.SimpleMovieDto;
import ssafy.moviecurators.service.CommentService;
import ssafy.moviecurators.service.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSource messageSource;

    /**
     * 평가에 적힌 댓글 다 가져오기[GET]
     * @param id 댓글을 가져오고 싶은 평가의 id
     * @return 해당 평가에 달린 댓글들을 http response body에 넣어서 보냄
     */
    @GetMapping("/movies/{articleId}/comments/list/")
    public ResponseEntity<List<CommentDto>> commentList(@PathVariable("articleId") Long id) {
        List<Comment> comments = commentService.commentList(id);
        if(comments.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<CommentDto> result = comments.stream()
                .map(c -> new CommentDto(c))
                .collect(toList());

        return ResponseEntity.ok().body(result);
    }

    /**
     * 댓글 작성
     * @param articleId 작성하고 싶은 댓글을 가지고 있는 평가의 id[POST]
     * @param comment 사용자가 작성한 댓글
     * @param request
     * @return 새로 생성한 댓글을 http response body에 넣어서 보냄
     */
    @PostMapping("/movies/{articleId}/comments/")
    public ResponseEntity commentDetailPost(@PathVariable("articleId") Long articleId,
                                                @RequestBody Comment comment,
                                                HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        commentService.commentDetailPost(comment, articleId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    /**
     * 댓글 수정
     * @param articleId 수정하고 싶은 댓글을 가지고 있는 평가의 id[PUT]
     * @param obj 수정된 댓글을 가지고 있는 collection
     * @param request
     * @return 수정된 댓글을 http response body에 넣어서 보냄
     */
    @PutMapping("/movies/{articleId}/comments/")
    public ResponseEntity commentDetailPut(@PathVariable("articleId") Long articleId,
                                                  @RequestBody Map<String, Object> obj,
                                                  HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        // 애초에 Map<String, String> 안쓰고 Obj 사용해서 더 복잡해짐
        Long commentId = Long.parseLong(String.valueOf(obj.get("commentId")));
        String newContent = String.valueOf(obj.get("content"));

        commentService.commentDetailPut(commentId, newContent);

        return ResponseEntity.ok().body(null);
    }

    /**
     * 댓글 삭제
     * @param articleId 삭제하고 싶은 댓글을 가지고 있는 평가의 id[DELETE]
     * @param obj 삭제하고 싶은은 댓글을 가지고 있는 collection
     * @param request
     * @return 삭제여부를 http response body에 넣어서 보냄
     */
    @DeleteMapping("/movies/{articleId}/comments/")
    public ResponseEntity commentDetailDelete(@PathVariable("articleId") Long articleId,
                                                    @RequestBody Map<String, String> obj,
                                                    HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        if(!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        }
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        Long commentId = Long.parseLong((obj.get("commentId")));

        /**
         * 삭제하기
         */
        try {
            commentService.commentDetailDelete(userId, commentId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        /**
         * 삭제권한 없음
         */
        catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.authorization", null, LocaleContextHolder.getLocale())));
        }


    }
}
