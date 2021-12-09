package ssafy.moviecurators.domain.movies;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies_genre")  // Django식 네이밍
@Getter @Setter  // setter 나중에 이동
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre {

    @Id @GeneratedValue
    private Integer id;

    @Size(max=50)
    private String name;

    @ManyToMany(mappedBy = "genre_ids")
    private List<Movie> movies = new ArrayList<>();

}
