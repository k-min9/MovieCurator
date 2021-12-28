package ssafy.moviecurators.domain.movies;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Movie Entity
 */
@Entity
@Table(name = "movies_movie")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Movie {

    @Id @GeneratedValue
    private Long id;

    @Size(max=200)
    private String backdrop_path;

    @Size(max=200)
    private String poster_path;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(name="release_date")
    private Date releaseDate;

    @Size(max=100)
    @Column(name = "original_title")
    private String originalTitle;

    @Size(max=100)
    private String title;

    private Double popularity;

    private Integer vote_count;

    @Column(name = "vote_average")
    private Double voteAverage;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Integer> movie_reference_overview;


    @OneToMany(mappedBy = "movie")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<MovieGenre> movieGenres = new ArrayList<>();

}
