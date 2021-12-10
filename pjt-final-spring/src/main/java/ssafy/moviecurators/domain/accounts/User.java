package ssafy.moviecurators.domain.accounts;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.domain.movies.Likes;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "accounts_user")  // Django식 네이밍
@Builder
@Getter @Setter
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User implements UserDetails {

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

    @Column(columnDefinition = "TEXT")
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

    // Security UserDetail 관련
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 생성자
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    // 패스워드 해싱
    @Transactional
    public void encodePassword(PasswordEncoder passwordEncoder){
        password = passwordEncoder.encode(password);
    }
}
