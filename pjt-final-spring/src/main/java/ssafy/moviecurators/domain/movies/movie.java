package ssafy.moviecurators.domain.movies;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "movies_movie")  // Django식 네이밍
@Getter @Setter  // setter 나중에 이동
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class movie {

    @Id @GeneratedValue
    //@Column(name = "movie_id")
    private Long id;

    @Size(max=200)
    private String backdrop_path;

    @Size(max=200)
    private String poster_path;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private Date release_date;

    @Size(max=100)
    private String original_title;

    @Size(max=100)
    private String title;

    private Double popularity;

    private Integer vote_count;

    private Double vote_average;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Integer> movie_reference_overview;
}
