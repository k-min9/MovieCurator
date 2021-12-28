package ssafy.moviecurators.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.moviecurators.domain.movies.Comment;

import java.util.List;

/**
 * 댓글 관련 Repository, JpaRepository 상속
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.article a where a.id = :articleId order by c.mileage desc, c.id desc")
    List<Comment> commentList(@Param("articleId") Long id);

    @Query("select c from Comment c " +
            "join fetch c.article a " +
            "join fetch c.user u " +
            "where a.id = :articleId and u.id = :userId")
    Comment commentDetail(@Param("articleId") Long articleId, @Param("userId") Long userId);
}
