package ssafy.moviecurators.dto.simple;

import lombok.Data;
import ssafy.moviecurators.domain.movies.Article;

import java.time.OffsetDateTime;

@Data
public class SimpleArticleDto {

    private Long id;
    private Double rate;
    private String title;
    private String content;
    private Integer points;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    public SimpleArticleDto(Article article) {
        this.id = article.getId();
        this.rate = getRate();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.points = article.getPoints();
        this.created_at = article.getCreated();
        this.updated_at = article.getUpdated();
    }
}
