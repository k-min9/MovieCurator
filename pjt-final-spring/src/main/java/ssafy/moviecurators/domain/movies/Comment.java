package ssafy.moviecurators.domain.movies;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Comment Entity
 */
@Entity
@Table(name = "movies_comment")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "thanks_content", columnDefinition = "TEXT")
    private String thanksContent = null;

    private Integer mileage = 0;

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
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
