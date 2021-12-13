package ssafy.moviecurators.domain.movies;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "movies_likes")  // Django식 네이밍
@Getter @Setter  // setter 나중에 이동
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class Likes {

    @Id @GeneratedValue
    private Long id;

    @Size(max = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String backdrop_path;

    //연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
