package ssafy.moviecurators.domain.accounts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.domain.movies.Likes;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts_user")  // Django식 네이밍
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
//    @Column(name = "user_id")  // 테이블 이름 변경됨
    private Long id;

    @Size(max=128)
    private String password;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @Null
    private OffsetDateTime last_login;

    private boolean is_superuser = false;

    @Size(max=150)
    private String username;

    @Size(max=150)
    private String first_name = "";

    @Size(max=150)
    private String last_name = "";

    @Email
    @Size(max=254)
    private String email = "";

    private boolean is_staff = false;
    private boolean is_active = true;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime date_joined;

    //https://stackoverflow.com/questions/67870747/spring-boot-entity-how-to-add-createddate-utc-timezone-aware-lastmodified
    @PrePersist
    private void beforeSaving() {
        date_joined = OffsetDateTime.now();
        //createdBy = Thread.currentThread().getName(); 필요하다면 추가
    }

    @Column(columnDefinition = "TEXT default '영화 애호가'")
    private String introduction = "영화 애호가";

    @Size(max = 20)
    private String nickname;

    @Size(max = 100)
    private String image = "";

    private Integer mileage = 0;
    private Integer exp = 0;
    
    // 연결
    @OneToMany(mappedBy = "to_user")
    private List<Curator> to_user = new ArrayList<>();

    @OneToMany(mappedBy = "from_user")
    private List<Curator> from_user = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likes> likes = new ArrayList<>();

    // 생성자
    public User(String username, String password, String nickname) {
        super();
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
