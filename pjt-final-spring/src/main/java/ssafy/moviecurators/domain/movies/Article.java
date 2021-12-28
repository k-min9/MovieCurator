package ssafy.moviecurators.domain.movies;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Article Entity
 */
@Entity
@Table(name = "movies_article")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id @GeneratedValue
    private Long id;

    private Double rate;

    @Size(max = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer points = 0;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime created;

    @PrePersist
    private void beforeSaving() {
        created = OffsetDateTime.now();
        updated = OffsetDateTime.now();
    }

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updated;

    @PreUpdate
    private void beforeUpdating() {
        updated = OffsetDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<Likes> likes = new ArrayList<>();

}
