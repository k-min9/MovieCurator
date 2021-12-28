package ssafy.moviecurators.dto.simple;

import lombok.Data;
import ssafy.moviecurators.domain.movies.Likes;

/**
 * 좋아요 관련 DTO
 */
@Data
public class SimpleLikesDto {

    private Long id;
    private String title;
    private String backdrop_path;

    public SimpleLikesDto(Likes likes) {
        this.id = likes.getId();
        this.title = likes.getTitle();
        this.backdrop_path = likes.getBackdrop_path();
    }
}
