package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.dto.simple.SimpleArticleDto;

import java.sql.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class MovieDto {

    private Long id;
    private String backdrop_path;
    private String poster_path;
    private String overview;
    private Date release_date;
    private String original_title;
    private String title;
    private Double popularity;
    private Integer vote_count;
    private Double vote_average;
    private List<Integer> movie_reference_overview;
    private List<SimpleArticleDto> articles;

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.backdrop_path = movie.getBackdrop_path();
        this.poster_path = movie.getPoster_path();
        this.overview = movie.getOverview();
        this.release_date = movie.getRelease_date();
        this.original_title = movie.getOriginal_title();
        this.title = movie.getTitle();
        this.popularity = movie.getPopularity();
        this.vote_count = movie.getVote_count();
        this.vote_average = movie.getVote_average();
        this.movie_reference_overview = movie.getMovie_reference_overview();
        this.articles = movie.getArticles() // 여기서 끝낼 경우 프록시로 빈값 뜰 줄 알았지만, 무한 루프
                .stream()
                .map(article -> new SimpleArticleDto(article))
                .collect(toList());
    }
}