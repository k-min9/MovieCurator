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

@Entity
@Table(name = "movies_movie")  // Django식 네이밍
@Getter @Setter  // setter 나중에 이동
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Movie {

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


    // 연결
    @OneToMany(mappedBy = "movie")
    private List<Article> articles = new ArrayList<>();

    // 예시용 다대다, 실무 금지!(일단 중간 테이블에 넣을 자료는 없음)
    // 실제와는 다르다. 이 부분은 상담? 필요?
    @ManyToMany
    @JoinTable(name = "movies_movie_genre_ids2",
        joinColumns = @JoinColumn(name= "movie_id"),
        inverseJoinColumns = @JoinColumn(name="genre_id"))
    private List<Genre> genre_ids = new ArrayList<>();



}
