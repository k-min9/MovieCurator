package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.dto.CommentDto;
import ssafy.moviecurators.dto.error.ErrorResponse;
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
     * 평가에 적힌 댓글 다 가져오기
     * */
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
     * 평가에 사용자가 적은 댓글 CRUD
     * get : 가져오기 >>>>>>>>>> 없음
     * post : 댓글 작성
     * put : 댓글 수정
     * delete : 댓글 삭제
     * */
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

    @DeleteMapping("/movies/{articleId}/comments/")
    public ResponseEntity articleDetailDelete(@PathVariable("articleId") Long articleId,
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

        commentService.commentDetailDelete(commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }



}
