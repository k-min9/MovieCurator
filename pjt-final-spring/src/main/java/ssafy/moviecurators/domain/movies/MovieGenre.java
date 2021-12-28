package ssafy.moviecurators.domain.movies;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.*;

/**
 * MovieGenre Entity
 */
@Entity
@Table(name = "movies_movie_genre_ids")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieGenre {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="genre_id")
    private Genre genre;
}
