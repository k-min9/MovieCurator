package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.domain.movies.Likes;
import ssafy.moviecurators.domain.movies.Movie;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ArticleDto {

    private Long id;
    private Double rate;
    private String title;
    private String content;
    private Integer points;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    // 연결
    private Movie movie;
    private User user;
    private List<Comment> comments;
    private List<Likes> likes;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.rate = article.getRate();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.points = article.getPoints();
        this.created_at = article.getCreated();
        this.updated_at = article.getUpdated();
//        this.movie = article.getMovie();
//        this.user = article.getUser();
//        this.comments = article.getComments().stream().collect(Collectors.toList());
//        this.likes = article.getLikes().stream().collect(Collectors.toList());
    }
}
