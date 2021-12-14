package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.dto.simple.SimpleUserDto;

import java.time.OffsetDateTime;

@Data
public class CommentDto {

    private Long id;
    private String content;
    private String thanks_content;
    private Integer mileage;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    // 연결
    //private SimpleArticleDto article;
    private SimpleUserDto user;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.thanks_content = comment.getThanksContent();
        this.mileage = comment.getMileage();
        this.created_at = comment.getCreated();
        this.updated_at = comment.getUpdated();

        // 연결 : 구현은 해 둠. 필요하면 사용.
        // this.article = new SimpleArticleDto(comment.getArticle());
        this.user = new SimpleUserDto(comment.getUser());
    }
}
