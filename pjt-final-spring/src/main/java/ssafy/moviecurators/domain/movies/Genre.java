package ssafy.moviecurators.domain.movies;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Genre Entity
 */
@Entity
@Table(name = "movies_genre")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre {

    @Id @GeneratedValue
    private Long id;

    @Size(max=50)
    private String name;

    @OneToMany(mappedBy = "genre")
    private List<MovieGenre> movieGenres = new ArrayList<>();

}
