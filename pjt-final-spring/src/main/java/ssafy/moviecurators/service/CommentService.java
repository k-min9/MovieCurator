package ssafy.moviecurators.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.repository.ArticleRepository;
import ssafy.moviecurators.repository.CommentRepository;
import ssafy.moviecurators.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@RequiredArgsConstructor  // 생성자 주입 처리
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public List<Comment> commentList(Long id) {
        return commentRepository.commentList(id);
    }

    @Transactional
    public void commentDetailPost(Comment comment, Long articleId, Long userId) {

        comment.setArticle(articleRepository.getById(articleId));
        comment.setUser(userRepository.getById(userId));
        commentRepository.save(comment);
    }

    @Transactional
    public void commentDetailPut(Long commentId, String newContent) {
        Comment comment = commentRepository.getById(commentId);
        comment.setContent(newContent);
    }

    @Transactional
    public void commentDetailDelete(Long userId, Long commentId) {
        Comment comment = commentRepository.getById(commentId);
        // 평가 작성자 내지 댓글 작성자가 삭제 가능
        if (comment.getUser().getId() == userId || comment.getArticle().getUser().getId() == userId) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }
    }
}
