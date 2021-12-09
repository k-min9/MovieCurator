package ssafy.moviecurators.domain.movies;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssafy.moviecurators.domain.accounts.User;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "movies_comment")  // Django식 네이밍
@Getter @Setter  // setter 나중에 이동
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String thanksContent = null;

    private Integer milleage = 0;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime created_at;

    //https://stackoverflow.com/questions/67870747/spring-boot-entity-how-to-add-createddate-utc-timezone-aware-lastmodified
    @PrePersist
    private void beforeSaving() {
        created_at = OffsetDateTime.now();
    }

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updated_at;

    @PreUpdate
    private void beforeUpdating() {
        updated_at = OffsetDateTime.now();
    }

    // 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
