package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.dto.ArticleDto;
import ssafy.moviecurators.dto.CommentDto;
import ssafy.moviecurators.dto.simple.SimpleArticleDto;
import ssafy.moviecurators.dto.simple.SimpleCommentDto;
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

    /**
     * 평가에 적힌 댓글 다 가져오기
     * */
    @GetMapping("/movies/{articleId}/comments/list/")
    public List<CommentDto> commentList(@PathVariable("articleId") Long id) {
        List<Comment> comments = commentService.commentList(id);

        List<CommentDto> result = comments.stream()
                .map(c -> new CommentDto(c))
                .collect(toList());
        return result;
    }

    /**
     * 평가에 사용자가 적은 댓글 CRUD
     * get : 가져오기
     * post : 댓글 작성
     * put : 댓글 수정
     * delete : 댓글 삭제
     * */
    @PostMapping("/movies/{articleId}/comments/")
    public ResponseEntity<Long> commentDetailPost(@PathVariable("articleId") Long articleId,
                                        @RequestBody Comment comment,
                                        HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        commentService.commentDetailPost(comment, articleId, userId);

        return new ResponseEntity<>(articleId, HttpStatus.CREATED);
    }


    @PutMapping("/movies/{articleId}/comments/")
    public ResponseEntity<Long> commentDetailPut(@PathVariable("articleId") Long articleId,
                                                  @RequestBody Map<String, Object> obj,
                                                  HttpServletRequest request) {

        // 평가 작성자 또는 본인인지 체크하는 것 없음 쓸데없이 articleId 등 나중에 활요 하던가...
        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        // 애초에 Map<String, String> 쓰던가...
        Long commentId = Long.parseLong(String.valueOf(obj.get("commentId")));
        String newContent = String.valueOf(obj.get("content"));

        commentService.commentDetailPut(commentId, newContent);

        return new ResponseEntity<>(articleId, HttpStatus.CREATED);
    }

    @DeleteMapping("/movies/{articleId}/comments/")
    public ResponseEntity<Long> articleDetailDelete(@PathVariable("articleId") Long articleId,
                                                    @RequestBody Map<String, String> obj,
                                                    HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        Long commentId = Long.parseLong((obj.get("commentId")));

        commentService.commentDetailDelete(commentId);

        return new ResponseEntity<>(commentId, HttpStatus.NO_CONTENT);
    }



}
