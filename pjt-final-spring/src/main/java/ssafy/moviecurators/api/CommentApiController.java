package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.dto.ArticleDto;
import ssafy.moviecurators.dto.CommentDto;
import ssafy.moviecurators.dto.simple.SimpleCommentDto;
import ssafy.moviecurators.service.CommentService;
import ssafy.moviecurators.service.JwtTokenProvider;

import java.util.List;

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
}
