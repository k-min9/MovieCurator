package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.movies.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 평가에 적힌 모든 댓글들
    @Query("select c from Comment c join fetch c.article a where a.id = :articleId order by c.mileage desc, c.id desc")
    List<Comment> commentList(@Param("articleId") Long id);

    // 신청자의 댓글
    @Query("select c from Comment c " +
            "join fetch c.article a " +
            "join fetch c.user u " +
            "where a.id = :articleId and u.id = :userId")
    Comment commentDetail(@Param("articleId") Long articleId, @Param("userId") Long userId);
}
