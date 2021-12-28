package ssafy.moviecurators.domain.movies;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Likes Entity
 */
@Entity
@Table(name = "movies_likes")
@Getter @Setter
@NoArgsConstructor
public class Likes {

    @Id @GeneratedValue
    private Long id;

    @Size(max = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String backdrop_path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
