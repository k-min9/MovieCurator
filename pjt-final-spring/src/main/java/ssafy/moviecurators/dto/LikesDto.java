package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.movies.Likes;

/**
 * 좋아요 관련 DTO
 */
@Data
public class LikesDto {

    private Long id;
    private String title;
    private String backdrop_path;
    private ArticleDto article;

    public LikesDto(Likes likes) {
        this.id = likes.getId();
        this.title = likes.getTitle();
        this.backdrop_path = likes.getBackdrop_path();
        this.article = new ArticleDto(likes.getArticle());
    }
}
