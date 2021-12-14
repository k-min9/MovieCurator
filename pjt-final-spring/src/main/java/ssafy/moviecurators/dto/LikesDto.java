package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.movies.Likes;
;

@Data
public class LikesDto {

    private Long id;
    private String title;
    private String backdrop_path;

    //연결
    private ArticleDto article;  // 믿기지 않지만 장고를 진짜 이렇게 짰음
    //private SimpleUserDto user;

    public LikesDto(Likes likes) {
        this.id = likes.getId();
        this.title = likes.getTitle();
        this.backdrop_path = likes.getBackdrop_path();

        // 연결
        this.article = new ArticleDto(likes.getArticle());
        //this.user = new SimpleUserDto(likes.getUser());
    }
}
