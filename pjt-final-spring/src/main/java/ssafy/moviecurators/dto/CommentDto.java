package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.dto.simple.SimpleUserDto;

import java.time.OffsetDateTime;

/**
 * 댓글 관련 DTO
 */
@Data
public class CommentDto {

    private Long id;
    private String content;
    private String thanks_content;
    private Integer mileage;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;
    private SimpleUserDto user;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.thanks_content = comment.getThanksContent();
        this.mileage = comment.getMileage();
        this.created_at = comment.getCreated();
        this.updated_at = comment.getUpdated();


//        this.article = new SimpleArticleDto(comment.getArticle());
        this.user = new SimpleUserDto(comment.getUser());

    }
}
