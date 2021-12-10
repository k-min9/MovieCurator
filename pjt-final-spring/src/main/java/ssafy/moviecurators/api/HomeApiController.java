package ssafy.moviecurators.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.service.MovieService;

import java.sql.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class HomeApiController {

    private final MovieService movieService;

    @GetMapping("/movies/")
    public List<MovieDto> home() {
        List<Movie> movies = movieService.homeMovie();
        List<MovieDto> result = movies.stream()
                .map(o -> new MovieDto(o))
                .collect(toList());

        return result;
    }

    // 현재 장고와 출력설정이 완전히 일치하지는 않음. 특히 아티클 부분
    @Data
    static class MovieDto {
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
        private List<ArticleDto> articles;

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
            //this.articles = movie.getArticles();  // 아마 프록시로 빈 값 뜰 듯? 해보니까 무한루프;;
            this.articles = movie.getArticles().stream()
                    .map(article -> new ArticleDto(article))
                    .collect(toList());
        }
    }

    // 무한루프 방지 + 필요한 내용물만 꺼내기
    @Data
    static class ArticleDto {
        private Long id;

        public ArticleDto(Article article) {
            this.id = article.getId();
        }
    }
}
